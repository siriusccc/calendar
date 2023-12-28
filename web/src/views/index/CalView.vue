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
      <div>{{ imageUrlss }}</div>
      <div v-for="(imageUrl, index) in imageUrlss" :key="index">
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
        <el-form-item label="上传图片">
          <el-upload
            class="pic-uploader" 
            :http-request="handleHttpRequest"             
            :data="formData"

          >
            <el-button size="big" type="primary" style="width: 100%;">
              点击上传
            </el-button>
          </el-upload>
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
// import { client } from '../../alioss'

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
      fileList: [],
      Addfrom:{
          url:''
        }
    }
  },
  methods: {
    handleClick(date) {
      this.getImage(date);
      this.clickCount ++;
      if (this.clickCount === 1) {
        this.clickTimer = setTimeout(() => {
          // 单击事件处理逻辑
          console.log(this.imageUrlss);
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
      this.day = date;
      console.log(this.day)
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
    // handleBeforeUpload(file) {
		// 		const isJPEG = file.name.split('.')[1] === 'jpeg';
		// 		const isJPG = file.name.split('.')[1] === 'jpg';
		// 		const isPNG = file.name.split('.')[1] === 'png';
		// 		const isWEBP = file.name.split('.')[1] === 'webp';
		// 		const isGIF = file.name.split('.')[1] === 'gif';
		// 		const isLt500K = file.size / 1024 / 1024 / 1024 / 1024 < 4;
		// 		if (!isJPG && !isJPEG && !isPNG && !isWEBP && !isGIF) {
		// 			this.$message.error('上传图片只能是 JPEG/JPG/PNG 格式!');
		// 		}
		// 		if (!isLt500K) {
		// 			this.$message.error('单张图片大小不能超过 4mb!');
		// 		}
		// 		return (isJPEG || isJPG || isPNG || isWEBP || isGIF) && isLt500K;
		// 	},
    // uploadURL(file) {
    //   //注意哦，这里指定文件夹'image/'，尝试过写在配置文件，但是各种不行，写在这里就可以
    //   var fileName = 'image/'+'banner' + `${Date.parse(new Date())}` + '.jpg'; 
    //   //定义唯一的文件名，打印出来的uid其实就是时间戳
    //   client().multipartUpload(fileName, file.file,{
    //           progress: function(percentage) {
    //             console.log('打印进度',percentage)
    //           }
    //         }).then(()=>{
    //     //此处赋值，是相当于上传成功之后，手动拼接服务器地址和文件名
    //     //简单描述就是bucket概括，里面的域名地址，粘贴过来可以直接用
    //     this.Addfrom.url = "https://jeff-pic.oss-cn-beijing.aliyuncs.com/" + fileName;
    //     console.log(this.Addfrom.url,"this.Addfrom.url")
    //   })
    // },
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

    let imageUrlss = ref([]);
    const day = ref(null);
    const uploadFile = ref({
      date: null,
      file: null,
    });

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

    const getImage = date => {
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
          imageUrlss.value = [];
          if(resp[0] != null) {
            for(var item of  resp) {
              imageUrlss.value.push(item.picurl);
            }
          } else {
            imageUrlss.value = ['https://jeff-pic.oss-cn-beijing.aliyuncs.com/f8261b0bf0bb4fa9b003c72c48af91d4.png',]
          }
        }
      }),
      console.log(imageUrlss);
    }

    const headersobj = {
      "Authorization": 'Bearer ' + store.state.user.token,
    }

    const handleHttpRequest = (file) => {
      console.log(file);
      console.log(day.value);
      let filedata = new FormData();
      filedata.append("file", file.file)
      filedata.append("date", day.value)
      // let date = day.value;
      $.ajax({
        url: "http://localhost:520/calendar/upload/",
        type: "post",
        data: filedata,
        processData: false,
        contentType : false,
        headers: {
          Authorization: "Bearer " + store.state.user.token,
        },
        success(resp) {
          console.log(resp);
        }
      })
    }

    const handleBeforeUpload = file => {
      uploadFile.value.date = day.value;
      uploadFile.value.file = file;
      return true;
    }

    const handleUploadSuccess = () => {
      const formData = new FormData();
      formData.append('date', uploadFile.value.date);
      formData.append('file', uploadFile.value.file);
      console.log(formData);

      $.ajax({
        url: "http://localhost:520/calendar/upload/",
        type: "post",
        data: formData,
        headers: {
          Authorization: "Bearer " + store.state.user.token,
        },
        success(resp) {
          console.log(resp);
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
      getImage,
      imageUrlss,
      headersobj,
      handleHttpRequest,
      day,
      uploadFile,
      handleBeforeUpload,
      handleUploadSuccess,
    }
  },
}
</script>

<style scoped>
.is-selected {
  color: #1989fa;
}
</style>
