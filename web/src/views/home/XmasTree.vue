<template>
    <div>
      <div class="calendar">
        <div class="calendar-header">
          <button @click="previousMonth">&lt;</button>
          <span>{{ currentMonth }}</span>
          <button @click="nextMonth">&gt;</button>
        </div>
        <div class="calendar-body">
          <div class="weekdays">
            <div v-for="day in daysOfWeek" :key="day" class="weekday">{{ day }}</div>
          </div>
          <div v-for="row in calendarRows" :key="row">
            <div v-for="day in row" :key="day.date" class="calendar-cell" @click="openUploadModal(day)">
              <span v-if="day.date">{{ day.date }}</span>
            </div>
          </div>
        </div>
      </div>
  
      <!-- 弹出的上传图片窗口 -->
      <div v-if="showUploadModal" class="upload-modal">
        <input type="file" @change="handleFileUpload" accept="image/*">
        <button @click="closeUploadModal">Close</button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        currentMonth: '',
        daysOfWeek: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
        calendar: [],
        showUploadModal: false,
        selectedDate: null,
        uploadedImage: null,
      };
    },
    mounted() {
      this.generateCalendar();
    },
    methods: {
      generateCalendar() {
        // 在这里生成日历数据，你可以使用日期库来简化日期操作，例如 day.js 或 moment.js
        // 这里的示例是简化的日历生成逻辑
        const today = new Date();
        const year = today.getFullYear();
        const month = today.getMonth();
        this.currentMonth = `${year} / ${month + 1}`;
  
        const daysInMonth = new Date(year, month + 1, 0).getDate();
        const firstDayOfMonth = new Date(year, month, 1).getDay();
  
        let date = 1;
        for (let i = 0; i < 6; i++) {
          const week = [];
          for (let j = 0; j < 7; j++) {
            if ((i === 0 && j < firstDayOfMonth) || date > daysInMonth) {
              week.push({ date: null });
            } else {
              week.push({ date });
              date++;
            }
          }
          this.calendar.push(week);
        }
      },
      previousMonth() {
        // 切换到上一个月
        // 可以在这里实现逻辑来更新日历数据
      },
      nextMonth() {
        // 切换到下一个月
        // 可以在这里实现逻辑来更新日历数据
      },
      openUploadModal(day) {
        if (day.date) {
          this.selectedDate = day.date;
          this.showUploadModal = true;
        }
      },
      closeUploadModal() {
        this.showUploadModal = false;
      },
      handleFileUpload(event) {
        const file = event.target.files[0];
        // 在这里处理上传的文件，你可以进行上传逻辑和保存数据的操作
        // 这里示例只是将上传的图片保存在 uploadedImage 中
        this.uploadedImage = URL.createObjectURL(file);
        // 这里可以根据业务逻辑将上传的图片与对应日期关联起来
        // 可以使用 selectedDate 变量来获取选定的日期
      },
    },
    computed: {
      calendarRows() {
        const rows = [];
        for (let i = 0; i < this.calendar.length; i++) {
          rows.push(this.calendar[i]);
        }
        return rows;
      },
    },
  };
  </script>
  
  <style>
  /* 样式可以根据需要自行调整 */
  .calendar {
    width: 300px;
    border: 1px solid #ccc;
    padding: 10px;
    text-align: center;
    font-family: Arial, sans-serif;
  }
  
  .calendar-header {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
  }
  
  .calendar-body {
    display: flex;
    flex-direction: column;
  }
  
  .weekdays {
    display: flex;
    justify-content: space-between;
    margin-bottom: 5px;
  }
  
  .weekday {
    width: 30px;
  }
  
  .calendar-cell {
    width: 30px;
    height: 30px;
    border: 1px solid #ccc;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
  }
  
  .upload-modal {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background: white;
    padding: 20px;
    border: 1px solid #ccc;
  }
  </style>
  