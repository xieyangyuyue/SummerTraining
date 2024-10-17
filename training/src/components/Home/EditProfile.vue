<template>
    <div class="edit-profile">
        <h1 class="title">修改个人信息</h1>
        <el-form :model="form" :rules="rules" ref="form" label-width="120px" class="form-container">
            <el-form-item label="名字" prop="name">
                <el-input v-model="form.name" placeholder="请输入名字" />
            </el-form-item>

            <el-form-item label="电话" prop="phone">
                <el-input v-model="form.phone" placeholder="请输入电话" />
            </el-form-item>

            <el-form-item label="性别" prop="sex">
                <el-radio-group v-model="form.sex">
                    <el-radio label="0">男</el-radio>
                    <el-radio label="1">女</el-radio>
                </el-radio-group>
            </el-form-item>

            <el-form-item class="button-group">
                <el-button type="primary" @click="save">保存</el-button>
                <el-button @click="cancel">取消</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
export default {
    name: 'EditProfile',
    data() {
        return {
            form: {
                id: '',
                name: '',
                phone: '',
                sex: '0'
            },
            rules: {
                name: [{ required: true, message: '请输入名字', trigger: 'blur' }],
                phone: [
                    { required: true, message: '请输入电话', trigger: 'blur' },
                    { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的手机号码', trigger: 'blur' }
                ],
                sex: [{ required: true, message: '请选择性别', trigger: 'change' }]
            }
        };
    },
    mounted() {
        const user = JSON.parse(sessionStorage.getItem('LoginUser'));
        if (user) {
            this.form.id = user.id;
            this.form.name = user.name;
            this.form.phone = user.phone;
            this.form.sex = user.sex;
            this.form.roleId = user.roleId;
        }
    },
    methods: {
        async save() {
            this.$refs.form.validate(async (valid) => {
                if (valid) {
                    try {
                        const response = await this.$http.post('user/update', this.form);
                        const result = response.data;
                        if (result.code === 200) {
                            this.$message({
                                message: '修改成功!',
                                type: 'success'
                            });

                            // Update session storage
                            sessionStorage.setItem('LoginUser', JSON.stringify(this.form));

                            // Emit event to notify Home.vue
                            this.$emit('userUpdated', this.form);

                            // Navigate back
                            this.$router.go(-1);
                        } else {
                            this.$message({
                                message: '操作失败!',
                                type: 'error'
                            });
                        }
                    } catch (error) {
                        this.$message({
                            message: '请求失败，请稍后重试。',
                            type: 'error'
                        });
                    }
                } else {
                    this.$message({
                        message: '校验失败，请填写完整数据',
                        type: 'error'
                    });
                }
            });
        },
        cancel() {
            this.$router.go(-1);
        }
    }
};
</script>

<style scoped>
.edit-profile {
    padding: 30px;
    max-width: 600px;
    margin: 0 auto;
    background: #ffffff;
    border-radius: 12px;
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
}

.title {
    margin-bottom: 20px;
    text-align: center;
    font-size: 26px;
    font-weight: bold;
    color: #333;
}

.form-container {
    background: #fafafa;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
}

.el-form-item {
    margin-bottom: 20px;
}

.el-form-item label {
    font-size: 16px;
    font-weight: 500;
    color: #555;
}

.el-form-item .el-input {
    width: 100%;
    border-radius: 8px;
    box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1);
    transition: border-color 0.3s, box-shadow 0.3s;
}

.el-form-item .el-input:focus {
    border-color: #409EFF;
    box-shadow: 0 0 4px rgba(64, 158, 255, 0.3);
}

.button-group {
    display: flex;
    justify-content: flex-end;
    gap: 12px;
}

.button-group .el-button {
    padding: 10px 20px;
    border-radius: 6px;
    font-size: 16px;
    transition: background-color 0.3s, box-shadow 0.3s;
}

.button-group .el-button[type="primary"] {
    background-color: #409EFF;
    border: none;
    color: #ffffff;
}

.button-group .el-button[type="primary"]:hover {
    background-color: #66b1ff;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.button-group .el-button:not(.el-button--primary) {
    background-color: #ffffff;
    border: 1px solid #dcdfe6;
    color: #606266;
}

.button-group .el-button:not(.el-button--primary):hover {
    background-color: #f5f7fa;
    border-color: #c0c4cc;
}
</style>
