<template>
  <ContentField style="text-align: center;">
    <el-calendar>
      <template #date-cell="{ data }">
        <p :class="data.isSelected ? 'is-selected' : ''">
          {{ data.day.split('-').slice(1).join('-') }}
          {{ data.isSelected ? '✔️' : '' }}
        </p>
      </template>
    </el-calendar>
    <el-calendar :range="[new Date(2019, 2, 4), new Date(2019, 2, 24)]" />
  </ContentField>
  </template>

  <script>
  import ContentField from '../../components/ContentField.vue'

  export default {
    components: {
        ContentField
    },
  }
  </script>
  
  <style scoped>
  .is-selected {
    color: #1989fa;
  }
  </style>
  