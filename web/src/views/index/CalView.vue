<template>
  <ContentField style="text-align: center;">
    <el-calendar>
      <template #date-cell="{ data }">
        <div style="height: 100%;">
          <!-- <el-popover
            trigger="hover"
            placement="top"
            :content="getContent(data)"
            :disabled="!data.day"> -->
            <div 
              style="height: 100%;" 
              :class="data.isSelected ? 'is-selected' : ''" 
              @mouseover="hoverContent(data.day)" 
              @click="handleClick(data.day)" 
              @dblclick="handledbClick(data.day)"
            >
              <div>{{ data.day.split('-').slice(1).join('-') }}</div>
              <div v-if="data.day === content.date">{{ content.content }}</div>
              <div v-else-if="!data.day === content.date"> {{ contentnull }}</div>
            </div>
          <!-- </el-popover> -->
        </div>
      </template>
    </el-calendar>
    <!-- <el-popover placement="top-start" :content="hoverDate.value"> -->
    
    <el-drawer v-model="clickdrawer" :title="title" :show-close="true">
      <div v-for="(imageUrl, index) in imageUrls" :key="index">
        <img :src="imageUrl" alt="图片" style="margin-bottom: 10px;" />
      </div>
      
      <template #footer>
        <el-button type="danger" @click="clickdrawer = false">cancel</el-button>
      </template>
    </el-drawer>

    <el-dialog v-model="dialogFormVisible" title="安排">
      <el-form :model="calendar" :label-width="100">
        <el-form-item label="添加内容">
          <el-input v-model="calendar.content" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-tooltip placement="top" content="cancel">
              <button class="btn"
              data-bs-toggle="popover"
              data-bs-trigger="hover focus"
              data-bs-placement="top"
              data-bs-content="cancel"
              @click="dialogFormVisible = false">取消</button>
          </el-tooltip>  

          <el-popover
            placement="top-start"
            title="功能"
            :width="200"
            trigger="hover"
            content="确定"
          >
            <template #reference>
              <el-button type="primary" @click="save(calendar)">确定</el-button>
            </template> 
          </el-popover>

          <el-tooltip placement="top" content="信息">
            <el-popover
            placement="top-start"
            title="功能"
            :width="200"
            trigger="hover"
            content="信息"
          >
          <template #reference>
            <el-button @click="visible = true">Open Drawer</el-button>
          </template> 
          </el-popover>
          </el-tooltip>
          <el-drawer v-model="visible" :show-close="false">
            <template #header="{ close }">
          <!-- <h4 :id="titleId" :class="titleClass">This is a custom header!</h4> -->
              <el-button type="danger" @click="close">
          <!-- <el-icon class="el-icon--left"><CircleCloseFilled /></el-icon> -->
                  Close
              </el-button>
            </template>
          </el-drawer>

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
      ContentField,
  },
  data() {
    return {
      value: new Date(),
      dialogFormVisible: false,
      clickdrawer: false,
      calendar: {},
      clickCount: 0,
      clickTimer: null,
      title: "",
      imageUrls: ['https://jeff-pic.oss-cn-beijing.aliyuncs.com/f8261b0bf0bb4fa9b003c72c48af91d4.png',
                  'https://jeff-pic.oss-cn-beijing.aliyuncs.com/f8261b0bf0bb4fa9b003c72c48af91d4.png'],
    }
  },
  methods: {
    handleClick(date) {
      this.clickCount ++;
      if (this.clickCount === 1) {
        this.clickTimer = setTimeout(() => {
          // 单击事件处理逻辑
          this.clickdrawer = true;
          this.calendar = {date: date};
          this.title = date;
          this.clickCount = 0;
        }, 200);
      }
    },
    handledbClick(date) {
      clearTimeout(this.clickTimer);
      this.clickCount = 0;
      // 双击事件处理逻辑
      console.log('Double click event');
      this.dialogFormVisible = true;
      this.calendar = {date: date};
    },
    handleMouseOver(date) {
      this.hoverDate = date;
    },
    getContent(data) {
      if (data.day) {
        return data.day;
      }
      return '';
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
    const visible = ref(false);

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
      visible,
    }
  },
}
</script>

<style scoped>
.is-selected {
  color: #1989fa;
}
</style>
