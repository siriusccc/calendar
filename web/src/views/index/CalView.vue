<template>
  <ContentField style="text-align: center;">
    <el-calendar>
      <template #date-cell="{ data }">
        <div style="height: 100%;" :class="data.isSelected ? 'is-selected' : ''" @click="loadCal(data.day)" @dblclick.prevent="handleClick(data.day)">
          <p>{{ data.day.split('-').slice(1).join('-') }}</p>
          <!-- <div v-if="arr.find(v => v.date === data.day)">{{ arr.find(v => v.date === data.day).content }}</div> -->
          <div v-if="data.day === content.date">{{ content.content }}</div>
          <div v-else-if="!data.day === content.date"> {{ contentnull }}</div>
        </div>
      </template>
    </el-calendar>

    <el-dialog v-model="dialogFormVisible" title="安排">
    <el-form :model="calendar" :label-width="100">
      <el-form-item label="添加内容">
        <el-input v-model="calendar.content" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save(calendar)">确定</el-button>
      </span>
    </template>
  </el-dialog>

  </ContentField>
</template>

<script>
import ContentField from '../../components/ContentField.vue'
import $ from 'jquery'
import { ref, getCurrentInstance } from 'vue'
import { useStore } from 'vuex';

export default { 
  components: {
      ContentField
  },
  data() {
    return {
      value: new Date(),
      dialogFormVisible: false,
      calendar: {},
    }
  },
  methods: {
    handleClick(date) {
      this.dialogFormVisible = true;
      this.calendar = {date: date};
    },
  },

  setup() { 
    const dialogForm = getCurrentInstance();
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
          } else {
            content.value = resp[0];
            // console.log(resp);
            // console.log(content);
            // console.log(content.value.date);
            // console.log(content.value.content);
          }
        }
      })
    }

    const save = info => {
      $.ajax({
        url: "http://localhost:520/calendar/addinfo/",
        type: "post",
        data:{
            date: info.date,
            content: info.content,
        },
        headers:{
            Authorization: "Bearer " + store.state.user.token,
        },
        success(resp){
            if(resp.error_message == "success") {
                dialogForm.data.dialogFormVisible = false;
            }
        }
      })
    }

    return{
      content,
      contentnull,
      loadCal,
      save
    }
  },
}
</script>

<style scoped>
.is-selected {
  color: #1989fa;
}
</style>
