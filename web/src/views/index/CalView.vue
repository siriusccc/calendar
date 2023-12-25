<template>
  <ContentField style="text-align: center;">
    <el-calendar>
      <template #date-cell="{ data }">
        <el-tooltip placement="top" :content="hoverDate.value">
          <div style="height: 100%;" :class="data.isSelected ? 'is-selected' : ''" @mouseover="hoverContent(data.day)" @click="loadCal(data.day)" @dblclick.prevent="handleClick(data.day)">
            <div>{{ data.day.split('-').slice(1).join('-') }}</div>
            <!-- <div v-if="arr.find(v => v.date === data.day)">{{ arr.find(v => v.date === data.day).content }}</div> -->
            <div v-if="data.day === content.date">{{ content.content }}</div>
            <div v-else-if="!data.day === content.date"> {{ contentnull }}</div>
          </div>
        </el-tooltip>
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
import { ref, getCurrentInstance, reactive } from 'vue'
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
      store: useStore(),
    }
  },
  methods: {
    handleClick(date) {
      this.dialogFormVisible = true;
      this.calendar = {date: date};
    },
    handleMouseOver(date) {
      this.hoverDate = date;
    },
  },

  setup() { 
    const dialogForm = getCurrentInstance();
    const store = useStore();
    let content = ref([]);
    let contentnull = ref();
    const hoverDate = reactive({
      value: ''
    })

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
            console.log(resp);
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

    const hoverContent = date => {
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
            hoverDate.value = '空';
          } else {
            hoverDate.value = resp[0].content;
          }
        }
      })
    }

    return{
      content,
      contentnull,
      loadCal,
      save,
      hoverContent,
      hoverDate,
    }
  },
}
</script>

<style scoped>
.is-selected {
  color: #1989fa;
}
</style>
