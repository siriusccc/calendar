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
                <div class="card" style="width: 80%; height: 230px;"> 
                    <div id="main" style="width: 100%; height: 100%;"/>
                </div>
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
            <template #footer>
                <el-upload action="http://localhost:520/api/user/uploadbg/" 
                        :headers="headersobj"
                        :on-success="handleUploadBgSuccess">
                    <el-button type="primary">
                        添加背景
                    </el-button>
                </el-upload>
            </template>
        </el-dialog>
    </div>
</template>

<script>
import { onMounted, ref } from 'vue'
import { useStore } from 'vuex'
import $ from 'jquery'
import { ElMessage } from 'element-plus'

import * as echarts from 'echarts/core';
import {
  TitleComponent,
  CalendarComponent,
  TooltipComponent,
  VisualMapComponent
} from 'echarts/components';
import { HeatmapChart } from 'echarts/charts';
import { CanvasRenderer } from 'echarts/renderers';

echarts.use([
  TitleComponent,
  CalendarComponent,
  TooltipComponent,
  VisualMapComponent,
  HeatmapChart,
  CanvasRenderer
]);

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
    
    const handleUploadBgSuccess = (response) => {
      console.log(response.error_message);
      console.log(response.data);
    }

    function getVirtualData() {
        const data = [['2024-03-03',10], ['2024-03-04',10], ['2024-03-05',10],
                        ['2024-03-06',10], ['2024-03-07',10], ['2024-03-08',10],
                        ['2024-03-09',10], ['2024-03-16',10], ['2024-03-23',10],
                        ['2024-03-30',10], ['2024-05-12',10], ['2024-05-19',10],
                        ['2024-05-06',10], ['2024-04-30',10], ['2024-05-01',10],
                        ['2024-05-02',10], ['2024-05-10',10], ['2024-05-18',10],
                        ['2024-05-25',10], ['2024-05-31',10], ['2024-05-27',10],
                        ['2024-06-04',10], ['2024-06-05',10], ['2024-06-06',10],
                        ['2024-07-07',10], ['2024-07-16',10], ['2024-07-08',10],
                        ['2024-07-17',10], ['2024-07-25',10], ['2024-07-26',10],
                        ['2024-08-03',10], ['2024-08-09',10], ['2024-08-08',10],
                        ['2024-08-13',10], ['2024-08-14',10], ['2024-08-19',10],
                        ['2024-08-18',10], ['2024-08-18',10], ['2024-09-15',10],
                        ['2024-09-16',10], ['2024-09-17',10], ['2024-09-18',10],
                        ['2024-09-19',10], ['2024-09-20',10], ['2024-09-21',10],
                        ['2024-09-22',10], ['2024-09-25',10], ['2024-09-29',10],
                        ['2024-09-28',10], ['2024-10-02',10], ['2024-10-05',10],
                        ['2024-10-06',10], ['2024-10-12',10], ['2024-10-13',10],
                        ['2024-10-09',10], ['2024-10-19',10] ];
            return data;
        }

        onMounted(() => {
            //页面加载完成后加载echarts
            var chartDom = document.getElementById('main');
            var myChart = echarts.init(chartDom);
            var option;
            
            option = {
                title: {
                    top: 30,
                    left: 'center',
                    text: 'Daily Count'
                },
                tooltip: {},
                visualMap: {
                    min: 0,
                    max: 10,
                    type: 'piecewise',
                    orient: 'horizontal',
                    left: 'center',
                    top: 65,
                    show: false
                },
                calendar: {
                    top: 90,
                    left: 30,
                    right: 30,
                    cellSize: ['auto', 13],
                    range: '2024',
                    itemStyle: {
                        borderWidth: 0.2
                    },
                    dayLabel: { show: false },
                    splitLine: {
                        show: true,
                        lineStyle:{
                            width: 0.3
                        }
                    },
                    monthLabel: { show: true },
                },
                series: {
                    type: 'heatmap',
                    coordinateSystem: 'calendar',
                    data: getVirtualData()
                }
            };

            myChart.setOption(option);
            //随页面动态缩放
            window.onresize = function () {
                myChart.resize()
            }
        });

    const addBg = () => {
        console.log("tianjiachengg");
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
        handleUploadSuccess,
        handleUploadBgSuccess,
        addBg
    }
  }
}
</script>

<style scoped>
div.card{
    margin-top: 20px;
    text-align: center;
}
.col-6 {
    width: 60%; /* 也可以根据需要调整具体的百分比 */
}
</style>