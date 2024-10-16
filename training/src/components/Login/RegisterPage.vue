<template>
    <div class="register-page">
      <el-card class="register-card" shadow="always">
        <h1 class="title">用户注册</h1>
        <el-form :model="registerForm" :rules="rules" ref="registerForm" label-width="120px">
          <el-form-item label="账号" prop="no" class="form-item">
            <el-input v-model="registerForm.no" autocomplete="off" class="input-field" />
          </el-form-item>
  
          <el-form-item label="密码" prop="password" class="form-item">
            <el-input type="password" v-model="registerForm.password" autocomplete="off" class="input-field" />
          </el-form-item>
  
          <el-form-item label="确认密码" prop="confirmPassword" class="form-item">
            <el-input type="password" v-model="registerForm.confirmPassword" autocomplete="off" class="input-field" />
          </el-form-item>
  
          <el-form-item>
            <div class="button-group">
              <el-button type="primary" @click="register" class="register-button">注册</el-button>
              <el-button @click="goBack" class="back-button">返回</el-button>
            </div>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        registerForm: {
          no: '',
          password: '',
          confirmPassword: '',
          roleId: '1',
          age: 18
        },
        rules: {
          no: [
            { required: true, message: '请输入账号', trigger: 'blur' },
            { min: 3, max: 8, message: '长度在 3 到 8个字符', trigger: 'blur' },
            { validator: this.checkDuplicate, trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 6, message: '密码长度不能少于 6 个字符', trigger: 'blur' },
            { validator: this.checkPasswordStrength, trigger: 'blur' }
          ],
          confirmPassword: [
            { required: true, message: '请确认密码', trigger: 'blur' },
            { validator: this.checkConfirmPassword, trigger: 'blur' }
          ]
        }
      };
    },
    methods: {
      checkDuplicate(rule, value, callback) {
        if (!value) {
          return callback(new Error('请输入账号'));
        }
  
        this.$http.get(`/user/findByNo?no=${value}`).then(res => res.data).then(res => {
          if (res.code === 200) {
            callback(new Error('账号已存在'));
          } else {
            callback();
          }
        }).catch(() => callback(new Error('验证失败')));
      },
      checkPasswordStrength(rule, value, callback) {
        const hasUpperCase = /[A-Z]/.test(value);
        const hasLowerCase = /[a-z]/.test(value);
  
        if (hasUpperCase && hasLowerCase) {
          callback();
        } else {
          callback(new Error('密码必须包含大小写字母'));
        }
      },
      checkConfirmPassword(rule, value, callback) {
        if (value !== this.registerForm.password) {
          callback(new Error('两次输入的密码不一致'));
        } else {
          callback();
        }
      },
      register() {
        this.$refs.registerForm.validate((valid) => {
          if (valid) {
            // Only store the password field
            const { confirmPassword, ...data } = this.registerForm;
            this.$http.post('user/save', data).then(res => res.data).then(res => {
              if (res.code === 200) {
                this.$message({
                  message: '注册成功!',
                  type: 'success'
                });
                this.$router.go(-1); // Go back to the previous page
              } else {
                this.$message({
                  message: '操作失败!',
                  type: 'error'
                });
              }
            }).catch(() => {
              this.$message({
                message: '请求失败，请稍后重试。',
                type: 'error'
              });
            });
          } else {
            this.$message({
              message: '校验失败，请填写完整数据',
              type: 'error'
            });
          }
        });
      },
      goBack() {
        this.$router.go(-1); // Go back to the previous page
      }
    }
  };
  </script>
  
  <style scoped>
  .register-page {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background: linear-gradient(135deg, #a2c2e0, #f4e1d2); /* Gradient background */
  }
  
  .register-card {
    width: 400px;
    padding: 20px;
    border-radius: 12px; /* Rounded corners for card */
    background: #ffffff;
  }
  
  .title {
    text-align: center;
    margin-bottom: 20px;
    font-size: 24px;
    color: #333;
  }
  
  .el-form-item {
    margin-bottom: 15px;
  }
  
  .form-item {
    display: flex;
    align-items: center;
  }
  
  .el-form-item__label {
    flex: 0 0 120px; /* Fixed width for labels */
    text-align: right; /* Align text to the right for labels */
  }
  
  .input-field {
    border-radius: 25px; /* Oval shape for input fields */
    border: 1px solid #ccc; /* Light border color */
    padding: 10px 15px; /* Adjust padding for aesthetics */
    font-size: 14px; /* Adjust font size */
    flex: 1; /* Make input field fill remaining space */
  }
  
  .button-group {
    display: flex;
    justify-content: space-between;
  }
  
  .register-button {
    width: 48%;
    border-radius: 50px; /* Oval shape for button */
    padding: 10px 20px; /* Adjust padding as needed */
    font-size: 14px; /* Adjust font size */
  }
  
  .back-button {
    width: 48%;
    background-color: #dcdcdc;
    border-radius: 50px; /* Oval shape for button */
    padding: 10px 20px; /* Adjust padding as needed */
    font-size: 14px; /* Adjust font size */
  }
  
  .el-card {
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  }
  </style>
  