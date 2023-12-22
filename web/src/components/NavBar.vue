<template>
  <el-menu
    ellipsis
    class="el-menu-popper-demo"
    mode="horizontal"
    :popper-offset="16"
    background-color="#545c64" 
    text-color="#fff" 
    active-text-color="#ffd04b"
    :router='true'
  >
    <el-menu-item index="/calendar/">Jeff</el-menu-item>
    <el-sub-menu index="2" :popper-offset="8" class="header-right">
      <template #title>下拉</template>
      <el-menu-item index="/pk/">子1</el-menu-item>
      <el-sub-menu index="3-4" :popper-offset="20">
        <template #title>子子</template>
        <el-menu-item index="/404/">子子1</el-menu-item>
      </el-sub-menu>
    </el-sub-menu>
    <div class="flex-grow" />
    <el-menu-item index="/user/login/">登录</el-menu-item>
    <el-menu-item index="/user/register/">注册</el-menu-item>
  </el-menu>
</template>

<script lang="ts" setup></script>

<style scoped>
.el-menu-popper-demo {
  /* background-color: hsl(159, 43%, 85%); */
  backdrop-filter: saturate(10%) blur(100px);
  width: 100%;
}
.flex-grow {
  flex-grow: 0.91;
}
</style>