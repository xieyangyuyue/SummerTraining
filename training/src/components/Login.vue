<template>
    <div class="loginBody">
      <div class="loginDiv">
        <div class="logo-container">
          <img src="@/assets/shengguang.jpg" alt="Logo" class="logo"/>
        </div>
        <div class="login-content">
          <h1 class="login-title">胜光运输管理系统</h1>
          <el-form :model="loginForm" label-width="100px" :rules="rules" ref="loginForm">
            <!-- 账号 -->
            <el-form-item label="账号" prop="no">
              <el-input type="text" v-model="loginForm.no" autocomplete="off" size="small" />
            </el-form-item>
            <!-- 密码 -->
            <el-form-item label="密码" prop="password">
              <el-input type="password" v-model="loginForm.password" show-password autocomplete="off" size="small" @change="confirm" />
            </el-form-item>
            <el-form-item>
              <div class="button-group">
                <el-button type="primary" @click="confirm" :disabled="confirm_disable">登录</el-button>
                <el-button type="danger" @click="navigateToRegister">注册</el-button>
              </div>
              <div class="link-group">
                <router-link to="/about-us" class="about-link">关于我们</router-link>
              </div>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'Login',
    data() {
      return {
        confirm_disable: false,
        loginForm: {
          no: '',
          password: ''
        },
        rules: {
          no: [{ required: true, message: '请输入账号', trigger: 'blur' }],
          password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
        }
      };
    },
    methods: {
      navigateToRegister() {
        this.$router.push('/register');
      },
      confirm() {
        this.confirm_disable = true;
        this.$refs.loginForm.validate((valid) => {
          if (valid) {
            this.$http.post('user/login', this.loginForm).then(res => res.data).then(res => {
              if (res.code === 200) {
                this.$message({ message: '登陆成功!', type: 'success' });
                sessionStorage.setItem("LoginUser", JSON.stringify(res.data.user));
                this.$store.commit("setMenu", res.data.menu);
                this.$router.replace('/Indextest');
              } else {
                this.$message({ message: '校验失败 用户名或密码错误!', type: 'error' });
                this.confirm_disable = false;
              }
            });
          } else {
            this.$message({ message: '校验失败 请输入合法的用户名或密码', type: 'error' });
            this.confirm_disable = false;
          }
        });
      }
    }
  }
  </script>
  
  <style scoped>
  .loginBody {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100vw;
    height: 100vh;
    background-color: #f5f5f5;
  }
  
  .loginDiv {
    width: 400px;
    padding: 20px;
    border-radius: 8px;
    background-color: #fff;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    position: relative;
  }
  
  .logo-container {
    display: flex;
    justify-content: center;
    margin-bottom: 20px;
  }
  
  .logo {
    width: 80px;
    height: 80px;
    border-radius: 50%;
    object-fit: cover;
    border: 4px solid #007bff;
  }
  
  .login-title {
    text-align: center;
    margin-bottom: 20px;
    font-size: 28px; /* 增加字体大小 */
    background: linear-gradient(45deg, #ff0081, #ff8c00, #00d2ff); /* 多巴胺色系渐变 */
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    text-shadow: 0 0 5px rgba(255, 0, 130, 0.8), 0 0 10px rgba(255, 140, 0, 0.8), 0 0 15px rgba(0, 210, 255, 0.8); /* 明显的阴影效果 */
  }
  
  .button-group {
    display: flex;
    justify-content: space-between;
  }
  
  .link-group {
    margin-top: 10px;
    text-align: right; /* Align the link to the right */
  }
  
  .about-link {
    font-size: 14px; /* 调整字体大小 */
    color: #007bff;
    text-decoration: underline;
  }
  </style>
  