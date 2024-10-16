<template>
  <div class="app-header">
    <div class="header-left">
      <el-icon style="font-size: 20px; cursor: pointer;" @click="collapse">
        <Fold />
      </el-icon>
    </div>
    <div class="header-center">
      <h1>欢迎来到运输管理系统</h1>
    </div>
    <span class="user-dropdown">
      {{ user.name }}
    </span>
    <div class="header-right">
      <el-dropdown>
        <el-icon style="margin-left: 5px; margin-top: 22px;">
          <user />
        </el-icon>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="toUser">个人中心</el-dropdown-item>
            <el-dropdown-item divided @click="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { Fold, User } from '@element-plus/icons-vue';

export default {
  name: 'Header',
  components: { Fold, User },
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem('LoginUser')) || {}
    };
  },
  methods: {
    collapse() {
      this.$emit('doCollapse');
    },
    toUser() {
      this.$router.push('/Home');
    },
    logout() {
      this.$confirm('你确定要退出登录', '提示', {
        confirmButtonText: '确定',
        type: 'warning',
        center: true
      }).then(() => {
        sessionStorage.clear();
        this.user = {}; // Clear user data
        this.$message({
          type: 'success',
          message: '退出登录成功'
        });
        this.$router.push('/');
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消退出登录'
        });
      });
    }
  },
  mounted() {
    // Ensure the user data is fetched when the component is mounted
    this.user = JSON.parse(sessionStorage.getItem('LoginUser')) || {};
  }
};
</script>

<style scoped>
.app-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px;
  background-color: #fff;
  border-bottom: 1px solid #e6e6e6;
}

.header-left .el-button {
  font-size: 20px;
}

.header-center {
  flex: 1;
  text-align: center;
}

.header-right .user-dropdown {
  cursor: pointer;
}
</style>
