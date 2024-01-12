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
        <el-menu-item index="/tree/">子子2</el-menu-item>
      </el-sub-menu>
    </el-sub-menu>
    <div class="flex-grow" />
    <ul class="navbar-nav" v-if="!$store.state.user.is_login">
      <el-sub-menu>
        <template #title>账户</template>
        <el-menu-item index="/user/login/">登录</el-menu-item>
        <el-menu-item index="/user/register/">注册</el-menu-item>
      </el-sub-menu>
    </ul>
    <ul class="navbar-nav" v-else-if="$store.state.user.is_login">
      <el-sub-menu>        
        <template #title>
          <el-avatar
          class="header-img"
          :src="$store.state.user.photo"
        />
        &nbsp; &nbsp;
          {{ $store.state.user.username }} 
        </template>
        <el-menu-item index="/info/">个人中心</el-menu-item>
        <el-menu-item @click="logout">退出登录</el-menu-item>
      </el-sub-menu>
    </ul>
    
  </el-menu>
</template>

<script>
import { useRoute } from 'vue-router'
import { computed } from 'vue'
import { useStore } from 'vuex';

export default {
    setup() {
        const store = useStore();
        const route = useRoute();
        let route_name = computed(() => route.name)

        const logout = () => {
          store.dispatch("logout");
          location.reload();
        }
        
        return {
          route_name,
          logout
        }
    }
}
</script>

<style scoped>
.el-menu-popper-demo {
  /* background-color: hsl(159, 43%, 85%); */
  backdrop-filter: saturate(10%) blur(100px);
  width: 100%;
}
.flex-grow {
  flex-grow: 0.91;
}
.header-img {
    width: 6vh;
    height: 6vh;
    margin-top: 5px;
    /* border-radius: 50%; */
}
</style>