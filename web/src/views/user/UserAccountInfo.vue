<template>
    <div class="content-field" 
    :style="{backgroundImage: 'url(' + selectBgImage + ')'}"
    style="background-size: cover; 
    /* background-attachment:fixed; */
        min-height:100vh; 
        min-width: 100vw;
        height: 100vh; 
        width: 99vw;
        position: flex;" >
        <div class="row">
            <div class="col-4">
                <div style="margin-right: 50px; margin-left: 50px; margin-top: 20px;">
                    <div class="avatar-card-body" style="margin-left: 40px; margin-right: 40px;">
                        <img :src="$store.state.user.photo" alt="" style="width: 100%; height: 100%; ">
                    </div>
                </div>
                <div class="card" style="margin-right: 100px; margin-left: 100px; margin-top: 10px;">
                    <div class="card-body">
                        <span style="font-style: italic; font-weight: bold;">你是：{{ $store.state.user.username }}</span>
                        <hr>
                        <!-- <el-upload action="http://localhost:520/api/user/uploadpic/" 
                        :headers="headersobj"
                        >
                            <el-button size="big" type="primary">
                                更换头像
                            </el-button>
                        </el-upload> -->
                        <el-button size="big" type="primary" @click="infodialogVisible = true">
                            更新信息
                        </el-button>
                        <el-button size="big" type="primary" @click="dialogVisible = true">
                            更换背景
                        </el-button>
                    </div>
                </div>
            </div>
            <div class="col-6">
                <div class="card"> dddd </div>
            </div>
        </div>

        <el-dialog v-model="infodialogVisible" title="更新信息">
            <el-form :label-width="100">
                <el-form-item label="更换id">
                    <el-input v-model="newusername" autocomplete="off" style="width: 200px; margin-right: 10px;" clearable/>
                    <el-button type="primary" @click="updateid(newusername)">确定</el-button>
                </el-form-item>
                <el-form-item label="更换头像">
                    <el-upload action="http://localhost:520/api/user/uploadpic/" 
                        :headers="headersobj"
                        :on-success="handleUploadSuccess">
                        <el-button size="big" type="primary">
                            更换头像
                        </el-button>
                    </el-upload>
                </el-form-item>
            </el-form>
        </el-dialog>
        <el-dialog v-model="dialogVisible" title="选择背景">
            <div>
                <el-image v-for="image in bgImages" :key="image"
                :src="require(`@/assets/images/${image}`)"
                style="width: 300px; height: 150px; margin-right: 20px; margin-bottom: 10px;"
                @click="selectImage(image)">
                </el-image>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import { onMounted, ref } from 'vue'
import { useStore } from 'vuex'
import $ from 'jquery'
import { ElMessage } from 'element-plus'

export default {
  components:{
  },

  setup() {
    const dialogVisible = ref(false);
    const infodialogVisible = ref(false);
    const bgImages = ref([]);
    const selectBgImage = ref(localStorage.getItem('backgroundImage')||'');
    const store = useStore();
    const newusername = ref();

    const selectImage = (image) => {
        selectBgImage.value = require(`@/assets/images/${image}`);
        localStorage.setItem('backgroundImage', selectBgImage.value);
    };

    const headersobj = {
        "Authorization": 'Bearer ' + store.state.user.token,
    };

    onMounted(() => {
        const imageFiles = require.context('@/assets/images', false, /.jpg$/).keys();
        const names = [];
        for(var name of imageFiles){
            names.push(name.split('/')[1]);
        }
        bgImages.value = names;
        const storedImage = localStorage.getItem('backgroundImage');
        if(storedImage){
            selectBgImage.value = storedImage;
        }
    });

    const updateid = (username) => {
        $.ajax({
        url: "http://localhost:520/api/user/updateid/",
        type: "post",
        data:{
            username: username,
        },
        headers:{
            Authorization: "Bearer " + store.state.user.token,
        },
        success(resp){
            infodialogVisible.value = false;
            store.commit('updateUsername', resp.data);
        }
      }),
      ElMessage({
          type: 'success',
          message: '更新成功',
        })
    }

    const handleUploadSuccess = (response) => {
      const newPhotoUrl = response.data;
      store.commit('updatePhoto', newPhotoUrl);
    }

    return{
        dialogVisible,
        infodialogVisible,
        bgImages,
        selectImage,
        selectBgImage,
        headersobj,
        updateid,
        newusername,
        handleUploadSuccess
    }
  }
}
</script>

<style scoped>
/* .content-field{
    background-image: url("@/assets/images/background3.jpg");
    background-size: cover;
    min-height:100vh;
    height: 100%;
    width: 100%; 
    position: flex;
} */
div.card{
    margin-top: 20px;
    text-align: center;
}
</style>