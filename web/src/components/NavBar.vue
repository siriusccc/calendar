<template>
  <el-menu
    ellipsis
    class="el-menu-popper-demo"
    mode="horizontal"
    :popper-offset="16"
    style="width: 100%"
    background-color="yellow"
  >
    <el-menu-item index="1">Jeff</el-menu-item>
    <el-sub-menu index="2" :popper-offset="8">
      <template #title>下拉</template>
      <el-menu-item index="3-1">子1</el-menu-item>
      <el-sub-menu index="3-4" :popper-offset="20">
        <template #title>子子</template>
        <el-menu-item index="3-4-1">子子1</el-menu-item>
      </el-sub-menu>
    </el-sub-menu>
  </el-menu>
</template>

<script lang="ts" setup></script>

<style scoped>
</style>