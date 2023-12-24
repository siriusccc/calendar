<template>
  <ContentField style="text-align: center;">
    <el-calendar>
      <template #date-cell="{ data }">
        <div style="height: 100%;" :class="data.isSelected ? 'is-selected' : ''" @click="loadCal(data.day)">
          <div>{{ data.day.split('-').slice(1).join('-') }}</div>
          <!-- <div v-if="arr.find(v => v.date === data.day)">{{ arr.find(v => v.date === data.day).content }}</div> -->
          <div v-if="data.day === content.date">{{ content.content }}</div>
          <div v-else-if="!data.day === content.date"> {{ contentnull }}</div>
        </div>
      </template>
    </el-calendar>
  </ContentField>
</template>

<script>
import ContentField from '../../components/ContentField.vue'
import $ from 'jquery'
import { ref } from 'vue';
import { useStore } from 'vuex';

export default { 
  components: {
      ContentField
  },
  data() {
    return {
      value: new Date(),
      arr: "",
    }
  },
  methods: {
    load(date) {
      $.ajax({
        url: "http://localhost:520/calendar/getinfo/",
        type: "get",
        data: {
          date,
        },
        // headers: {
        //   Authorization: "Bearer " + store.state.user.token,
        // },
        success(resp) {
          console.log(resp);
        },
        error(resp) {
          console.log(resp);
        }
      })
    },
    handleClick(date) {
      alert(date);
    }
  },
  setup() {
    const store = useStore();
    let content = ref([]);
    let contentnull = ref();

    const loadCal = date => {
      $.ajax({
        url: "http://localhost:520/calendar/getinfo/",
        type: "get",
        data: {
          date,
        },
        headers: {
          Authorization: "Bearer " + store.state.user.token,
        },
        success(resp) {
          if(resp[0] == null) {
            contentnull = "";
          }
          else {
            console.log(resp);
            content.value = resp[0];
            console.log(content);
            console.log(content.value.date);
            console.log(content.value.content);
          }
        }
      })
    }

    return{
      content,
      contentnull,
      loadCal,
    }
  },
}
</script>

<style scoped>
.is-selected {
  color: #1989fa;
}
</style>
