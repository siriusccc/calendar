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
      <div v-if="imageUrlss.length == 0">
        <el-empty>
          <el-upload
            class="pic-uploader" 
            :http-request="handleHttpRequest"             
            :data="formData"
            multiple
            accept=".jpg, .png"
          >
            <el-button type="primary">添加图片</el-button>
          </el-upload>
        </el-empty>
      </div>
      <div v-for="(imageUrl, index) in imageUrlss" :key="index" >
        <!-- <div>000000{{ imageUrl }}</div>
        <div>{{ imageUrlss.length }}</div> -->
        <div v-if="imageUrl.picurl == null">
          你的评价是：{{ imageUrl.content }}
        </div>
        <div v-else-if="imageUrl.content == null" class="demo-image__lazy">
          <!-- {{ imageUrl.picurl }} -->
          <span v-if="!isEditing" @click="startEditing('null')">暂无评价</span>
          <el-input class="inputdesc" v-else v-show="isEditing" v-model="editedText" @blur="stopEditing(imageUrl.picurl)" @keyup.enter="stopEditing(imageUrl.picurl)" />
          <el-image 
          :src="imageUrl.picurl" alt="图片" style="margin-bottom: 0px;" 
          :zoom-rate="1.2"
          :max-scale="7"
          :min-scale="0.2"
          :preview-src-list="imageUrls"
          />
          <div>
            <el-button style="margin-top: 0px;" type="danger" :icon="Delete" circle @click="confirmDelete(imageUrl.picurl)"/>
          </div>
        </div>

        <div v-else class="demo-image__lazy">
          <span v-if="!isEditing" @click="startEditing(imageUrl.content)">你的评价是：{{ imageUrl.content }}</span>
          <el-input class="inputdesc" v-else v-show="isEditing" v-model="editedText" @blur="stopEditing(imageUrl.picurl)" @keyup.enter="stopEditing(imageUrl.picurl)" />
          <el-image 
          :src="imageUrl.picurl" alt="图片" style="margin-bottom: 0px;" 
          :zoom-rate="1.2"
          :max-scale="7"
          :min-scale="0.2"
          :preview-src-list="imageUrls"
          />
          <div>
            <el-button type="danger" :icon="Delete" circle @click="confirmDelete(imageUrl.picurl)"></el-button>
          </div>
        </div>
      </div>
      <template #footer>
        <el-button type="danger" @click="clickdrawer = false">cancel</el-button>
      </template>
    </el-drawer>

    <el-dialog v-model="dialogFormVisible" title="插入内容">
      <el-form :model="calendar" :label-width="100">
        <el-form-item label="添加内容">
          <el-input v-model="calendar.content" autocomplete="off" />
        </el-form-item>
        <el-form-item label="上传图片">
          <el-upload
            class="pic-uploader" 
            :http-request="handleHttpRequest"             
            :data="formData"
            v-model:file-list="fileList"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            multiple
            accept=".jpg, .png">
            <!-- <el-button size="big" type="primary" style="width: 100%;">
              点击上传
            </el-button> -->
            <el-icon class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>

          <el-dialog v-model="dialogVisible">
            <img w-full :src="dialogImageUrl" alt="Preview Image" />
          </el-dialog>

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
            <!-- <el-button @click="visible = true">Open Drawer</el-button> -->
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
import { ref, getCurrentInstance, reactive, nextTick } from 'vue'
import { useStore } from 'vuex'
import { Delete, Plus } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

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
    }
  },
  methods: {
    handleClick(date) {
      this.getImage(date);
      this.clickCount ++;
      if (this.clickCount === 1) {
        this.clickTimer = setTimeout(() => {
          // 单击事件处理逻辑
          this.day = date;
          // console.log(this.imageUrlss);
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
      this.dialogFormVisible = true;
      this.calendar = {date: date};
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
    const hoverDate = reactive({value: ''})
    const visible = ref(false);

    let imageUrlss = ref();
    let imageUrls = ref();
    const day = ref(null);

    const fileList = ref([]);
    const dialogImageUrl = ref('');
    const dialogVisible = ref(false);

    const isEditing = ref(false);
    const editedText = ref("");
    const editInputRef = ref(null);

    const save = info => {
      $.ajax({
        url: "http://localhost:520/api/calendar/addinfo/",
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
        url: "http://localhost:520/api/calendar/getinfo/",
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
        url: "http://localhost:520/api/calendar/getinfo/",
        type: "get",
        data: {
          date,
        },
        headers: {
          Authorization: "Bearer " + store.state.user.token,
        },
        success(resp) {
          imageUrlss.value = [];
          imageUrls.value = [];
          if(resp[0] != null) {
            for(var item of  resp) {
              imageUrlss.value.push({"content":item.content,"picurl":item.picurl})
              if(item.picurl != null)
              imageUrls.value.push(item.picurl)
            }
          } 
        }
      })
    }

    const handleHttpRequest = (file) => {
      // console.log(day.value);
      // console.log(file);
      // console.log(file.file);

      let filedata = new FormData();
      filedata.append("file", file.file)
      filedata.append("date", day.value)
      $.ajax({
        url: "http://localhost:520/api/calendar/upload/",
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

    const handlePictureCardPreview = (uploadFile) => {
      dialogImageUrl.value = uploadFile.url
      dialogVisible.value = true
    }

    const startEditing = (content) => {
      isEditing.value = true;
      if(content == 'null') {
        editedText.value = "暂无评价";
      } else {
        editedText.value = content; // 保存原始文本
      }
      
      
      nextTick(() => {
        // 在调用 focus 之前检查 editInputRef.value 是否不为 null
        if (editInputRef.value) {
          editInputRef.value.focus(); // 使用 ref 聚焦输入框
        }
      });
    }

    const stopEditing = (img) => {
      let text;
      isEditing.value = false;
      if (editedText.value !== "") {
        text = editedText.value;
      }

      $.ajax({
        url: "http://localhost:520/api/calendar/addcontent/",
        type: "post",
        data:{
            picurl: img,
            content: text,
        },
        headers: {
          Authorization: "Bearer " + store.state.user.token,
        },
        success(resp) {
          console.log(resp);
        }
      })
    }

    const confirmDelete = (img) => {
      ElMessageBox.confirm(
        '真的要删除吗?',
        '确认删除',
        {
          confirmButtonText: '确定',
          cancelButtonText: '不确定',
          type: 'warning',
        }
      ).then(() => {
        console.log(img);
        deletePic(img);
        ElMessage({
          type: 'success',
          message: '删除成功',
        })
      }).catch(() => {
        ElMessage({
          type: 'info',
          message: '没删除',
        })
      })
    };

    const deletePic = (img) => {
      $.ajax({
        url: "http://localhost:520/api/calendar/delpic/",
        type: "post",
        data:{
            picurl: img
        },
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
      save,
      hoverContent,
      hoverDate,
      visible,
      getImage,
      imageUrls,
      imageUrlss,
      handleHttpRequest,
      day,
      fileList,
      dialogImageUrl,
      dialogVisible,
      handlePictureCardPreview,
      startEditing,
      stopEditing,
      isEditing,
      editedText,
      editInputRef,
      Delete,
      Plus,
      confirmDelete
    }
  },
}
</script>

<style scoped>
.is-selected {
  color: #1989fa;
}
.demo-image__lazy {
  height: 300px;
  overflow-y: auto;
}
.demo-image__lazy .el-image {
  display: block;
  min-height: 230px;
  margin-bottom: 100px;
}
.demo-image__lazy .el-image:last-child {
  margin-bottom: 0;
}
.inputdesc{
  width: 200px;
}
</style>
