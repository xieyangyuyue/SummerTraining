<template>
  <div class="loginBody">
    <div class="loginDiv">
      <div class="logo-container">
        <img src="@/assets/shengguang.jpg" alt="Logo" class="logo" />
      </div>
      <div class="login-content">
        <h1 class="login-title">胜光运输管理系统</h1>
        <el-form :model="loginForm" label-width="100px" :rules="rules" ref="loginForm">
          <!-- 账号 -->
          <el-form-item label="账号" prop="no" class="form-item">
            <el-input type="text" v-model="loginForm.no" autocomplete="off" size="small" class="input-field" />
          </el-form-item>
          <!-- 密码 -->
          <el-form-item label="密码" prop="password" class="form-item">
            <el-input type="password" v-model="loginForm.password" show-password autocomplete="off" size="small"
              class="input-field" />
          </el-form-item>
          <el-form-item>
            <div class="button-group">
              <el-button type="primary" class="oval-button" @click="confirm" :disabled="confirm_disable">登录</el-button>
              <el-button type="danger" class="oval-button" @click="register">注册</el-button>
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
              // this.$router.replace('/Indextest');
              this.$router.replace('/Home');
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
    },
    register() {
      this.navigateToRegister(); // Navigate to registration page
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
  background: linear-gradient(135deg, #a2c2e0, #f4e1d2);
  /* Gradient background */
}

.loginDiv {
  width: 400px;
  padding: 20px;
  border-radius: 12px;
  /* Slightly increased border radius */
  background: linear-gradient(145deg, #ffffff, #f0f0f0);
  /* Subtle gradient background */
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
  /* Softer shadow for a lifted effect */
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
  font-size: 28px;
  /* Increase font size */
  background: linear-gradient(45deg, #ff0081, #ff8c00, #00d2ff);
  /* Gradient text color */
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  text-shadow: 0 0 5px rgba(255, 0, 130, 0.8), 0 0 10px rgba(255, 140, 0, 0.8), 0 0 15px rgba(0, 210, 255, 0.8);
  /* Shadow effect */
}

.form-item {
  display: flex;
  align-items: center;
}

.el-form-item__label {
  flex: 0 0 100px;
  /* Set fixed width for labels */
  text-align: right;
  /* Align text to the right for labels */
}

.input-field {
  border-radius: 25px;
  /* Oval shape for input fields */
  border: 1px solid #ccc;
  /* Light border color */
  padding: 10px 15px;
  /* Adjust padding for aesthetics */
  font-size: 14px;
  /* Adjust font size */
  flex: 1;
  /* Make input field fill remaining space */
}

.button-group {
  display: flex;
  align-items: center;
  /* Ensure buttons and link are vertically centered */
}

.oval-button {
  border-radius: 50px;
  /* Create an oval shape */
  padding: 10px 20px;
  /* Adjust padding as needed */
  font-size: 14px;
  /* Adjust font size if needed */
}

.about-link {
  margin-left: 20px;
  /* Adjust this value as needed for exact 2cm spacing */
  font-size: 14px;
  color: #007bff;
  text-decoration: underline;
}
</style>
