<template>
  <el-menu
    ellipsis
    class="el-menu-popper-demo"
    mode="horizontal"
    :popper-offset="16"
    :router='true'
  >
    <el-menu-item index="1" index='/admin/users/personal'>Jeff</el-menu-item>
    <el-sub-menu class='header-right' index="2" :popper-offset="8">
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
.el-menu-popper-demo {
  background-color: hsl(159, 43%, 85%);
  backdrop-filter: saturate(180%) blur(10px);
  width: 100%;
}
</style>
