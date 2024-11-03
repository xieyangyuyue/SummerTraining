<template>
    <div>
        <!-- 列表查询 -->
        <div style="margin-bottom: 5px;">
            <el-input
                v-model="name"
                placeholder="请输入名字"
                suffix-icon="el-icon-search"
                style="width: 200px;"
                @change="loadPost"
            ></el-input>

            <el-select
                v-model="sex"
                filterable
                placeholder="请选择性别"
                suffix-icon="el-icon-user"
                style="width: 200px; margin-left: 5px;"
            >
                <el-option
                    v-for="item in sexs"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                ></el-option>
            </el-select>

            <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
            <el-button type="success" @click="resetParam">重置</el-button>
            <el-button type="danger" @click="add">新增</el-button>
        </div>

        <el-table
            :data="tableData"
            :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
            border
        >
            <el-table-column prop="id" label="Id" width="60" />
            <el-table-column prop="no" label="账号" width="160" />
            <el-table-column prop="name" label="姓名" width="160" />
            <el-table-column prop="age" label="年龄" width="160" />
            <el-table-column prop="sex" label="性别" width="160">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.sex === 1 ? 'primary' : 'success'" disable-transition>
                        {{ scope.row.sex === 1 ? '女' : '男' }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="roleId" label="角色" width="160">
                <template slot-scope="scope">
                    <el-tag :type="getRoleType(scope.row.roleId)" disable-transition>
                        {{ getRoleLabel(scope.row.roleId) }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="phone" label="电话" width="160" />
            <el-table-column prop="operate" label="操作" width="165">
                <template slot-scope="scope">
                    <el-button size="small" type="primary" @click="mod(scope.row)">编辑</el-button>
                    <el-popconfirm
                        confirm-button-text="确定"
                        cancel-button-text="取消"
                        title="确定删除吗?"
                        @confirm="del(scope.row.id)"
                    >
                        <template slot="reference">
                            <el-button size="small" type="danger">删除</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页处理 -->
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[2, 5, 10, 20]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
        >
        </el-pagination>

        <!-- Dialog对话框 -->
        <el-dialog
            :visible.sync="centerDialogVisible"
            title="新增"
            width="500"
            center
            :before-close="handleClose"
        >
            <el-form ref="form" :rules="rules" :model="form" label-width="auto" style="max-width: 600px">
                <el-form-item label="账号" style="width: 60%;" prop="no">
                    <el-input v-model="form.no" />
                </el-form-item>
                <el-form-item label="名字" style="width: 60%;" prop="name">
                    <el-input v-model="form.name" />
                </el-form-item>
                <el-form-item label="密码" style="width: 60%;" prop="password">
                    <el-input type="password" v-model="form.password" />
                </el-form-item>
                <el-form-item label="年龄" style="width: 60%;" prop="age">
                    <el-input v-model="form.age" />
                </el-form-item>
                <el-form-item label="性别">
                    <el-radio-group v-model="form.sex">
                        <el-radio value="0">男</el-radio>
                        <el-radio value="1">女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="电话" style="width: 60%;" prop="phone">
                    <el-input v-model="form.phone" />
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="centerDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="save">确定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: "Main",
    data() {
        let checkAge = (rule, value, callback) => {
            if (value > 150) {
                callback(new Error('年龄输入过大'));
            } else {
                callback();
            }
        };

        let checkDuplicate = (rule, value, callback) => {
            if (this.form.id) {
                return callback();
            }
            this.$http.get("/user/findByNo?no=" + this.form.no).then(res => res.data)
                .then(res => {
                    if (res.code != 200) {
                        callback();
                    } else {
                        callback(new Error('账号已存在'));
                    }
                });
        };

        return {
            tableData: [],
            pageSize: 10,
            pageNum: 1,
            total: 0,
            name: '',
            sex: '',
            sexs: [
                { value: '0', label: '男' },
                { value: '1', label: '女' }
            ],
            centerDialogVisible: false,
            form: {
                id: '',
                no: '',
                name: '',
                password: '',
                age: '',
                phone: '',
                sex: '0',
                roleId: '1'
            },
            rules: {
                no: [
                    { required: true, message: '请输入账号', trigger: 'blur' },
                    { min: 3, max: 8, message: '长度在 3 到 8个字符', trigger: 'blur' },
                    { validator: checkDuplicate, trigger: 'blur' }
                ],
                name: [
                    { required: true, message: '请输入名字', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 3, max: 8, message: '长度在 3 到 8个字符', trigger: 'blur' }
                ],
                age: [
                    { required: true, message: '请输入年龄', trigger: 'blur' },
                    { min: 1, max: 3, message: '长度在 1 到 3个位', trigger: 'blur' },
                    { pattern: /^([1-9][0-9]*){1,3}$/, message: '年龄必须为正整数', trigger: "blur" },
                    { validator: checkAge, trigger: 'blur' }
                ],
                phone: [
                    { required: true, message: '手机号不能为空', trigger: "blur" },
                    { pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号", trigger: "blur" }
                ]
            }
        };
    },
    methods: {
        del(id) {
            this.$http.get('user/delete?id=' + id).then(res => res.data).then(res => {
                if (res.code == 200) {
                    this.$message({
                        message: '操作成功!',
                        type: 'success'
                    });
                    this.loadPost();
                } else {
                    this.$message({
                        message: '操作失败!',
                        type: 'error'
                    });
                }
            });
        },
        mod(row) {
            this.centerDialogVisible = true;
            this.$nextTick(() => {
                this.form = { ...row, password: '' }; // Reset password field
            });
        },
        save() {
            this.$refs.form.validate(valid => {
                if (valid) {
                    if (this.form.id) {
                        this.doMod();
                    } else {
                        this.doSave();
                    }
                } else {
                    this.$message({
                        message: '校验失败 请填写完整数据',
                        type: 'error'
                    });
                }
            });
        },
        doSave() {
            this.$http.post('user/save', this.form).then(res => res.data).then(res => {
                if (res.code == 200) {
                    this.$message({
                        message: '操作成功!',
                        type: 'success'
                    });
                    this.centerDialogVisible = false;
                    this.loadPost();
                } else {
                    this.$message({
                        message: '操作失败!',
                        type: 'error'
                    });
                }
            });
        },
        doMod() {
            this.$http.post('user/update', this.form).then(res => res.data).then(res => {
                if (res.code == 200) {
                    this.$message({
                        message: '操作成功!',
                        type: 'success'
                    });
                    this.centerDialogVisible = false;
                    this.loadPost();
                } else {
                    this.$message({
                        message: '操作失败!',
                        type: 'error'
                    });
                }
            });
        },
        getRoleType(roleId) {
            if (roleId === 0) return 'danger';
            if (roleId === 1) return 'primary';
            return 'warning';
        },
        getRoleLabel(roleId) {
            if (roleId === 0) return '超级管理员';
            if (roleId === 1) return '管理员';
            return '用户';
        },
        resetParam() {
            this.name = '';
            this.sex = '';
        },
        loadPost() {
            this.$http.post('user/listPage', {
                pageSize: this.pageSize,
                pageNum: this.pageNum,
                param: {
                    name: this.name,
                    sex: this.sex
                }
            }).then(res => res.data).then(res => {
                if (res.code == 200) {
                    this.tableData = res.data;
                    this.total = res.total;
                } else {
                    this.$message.error('获取数据失败');
                }
            });
        },
        handleSizeChange(val) {
            this.pageNum = 1;
            this.pageSize = val;
            this.loadPost();
        },
        handleCurrentChange(val) {
            this.pageNum = val;
            this.loadPost();
        },
        add() {
            this.centerDialogVisible = true;
            this.$nextTick(() => {
                this.$refs.form.resetFields();
                this.form = {
                    id: '',
                    no: '',
                    name: '',
                    password: '',
                    age: '',
                    phone: '',
                    sex: '0',
                    roleId: '1'
                };
            });
        },
        // 可以添加 handleClose 和其他方法
    },
    beforeMount() {
        this.loadPost();
    }
};
</script>

<style>
/* 在此处添加自定义样式 */
</style>
