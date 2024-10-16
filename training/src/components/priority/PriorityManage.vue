<template>
    <div>
        <!-- 列表查询 -->
        <div style="margin-bottom: 5px;">
            <el-input v-model="name" placeholder="请输入公司名字" suffix-icon="Search" style="width: 200px;"
                @change="loadPost"></el-input>

            <el-input v-model="remark" placeholder="请输入备注" style="width: 200px;" @change="loadPost"></el-input>

            <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
            <el-button type="success" @click="resetParam">重置</el-button>
            <el-button type="danger" @click="add">新增</el-button>
        </div>

        <el-table :data="tableData" :header-cell-style="{ background: '#f2f5fc', color: '#555555' }" border
            v-loading="loading">
            <el-table-column prop="id" label="Id" width="60" />
            <el-table-column prop="name" label="公司名" width="160" sortable />
            <el-table-column prop="createtime" label="创建时间" sortable />
            <el-table-column prop="remark" label="备注" />
            <el-table-column prop="operate" label="操作" width="165">
                <template #default="scope">
                    <el-button size="small" type="primary" @click="mod(scope.row)">编辑</el-button>
                    <el-popconfirm confirm-button-text="确定" cancel-button-text="取消" title="确定删除吗?"
                        @confirm="del(scope.row.id)">
                        <template #reference>
                            <el-button size="small" type="danger">删除</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
            :page-sizes="[2, 5, 10, 20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>

        <el-dialog v-model="centerDialogVisible" title="提示" width="500" center :before-close="handleClose">
            <el-form ref="form" :rules="rules" :model="form" label-width="auto" style="max-width: 600px">
                <el-form-item label="公司名" style="width: 60%;" prop="name">
                    <el-input v-model="form.name" />
                </el-form-item>
                <el-form-item label="创建时间" style="width: 60%;" prop="createtime">
                    <el-date-picker v-model="form.createtime" type="datetime" format="yyyy-MM-dd HH:mm:ss" />
                </el-form-item>
                <el-form-item label="备注" style="width: 60%;" prop="remark">
                    <el-input type="textarea" v-model="form.remark" />
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="centerDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="save">确定</el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: "PriorityManage",
    data() {
        let checkDuplicate = (rule, value, callback) => {
            if (this.form.id) {
                return callback();
            }

            this.$http.get("/priority/findByNo?name=" + this.form.name).then(res => res.data)
                .then(res => {
                    if (res.code != 200) {
                        callback();
                    } else {
                        callback(new Error('公司名已存在'));
                    }
                })
        };

        return {
            tableData: [],
            pageSize: 10,
            pageNum: 1,
            total: 0,
            name: '',
            remark: '',
            loading: false,
            centerDialogVisible: false,
            form: {
                id: '',
                name: '',
                remark: '',
                createtime: ''
            },
            rules: {
                name: [
                    { required: true, message: '请输入公司名', trigger: 'blur' },
                    { min: 3, max: 20, message: '长度在 3 到 20个字符', trigger: 'blur' },
                    { validator: checkDuplicate, trigger: 'blur' }
                ],
                createtime: [
                    { required: true, message: '请输入创建时间', trigger: 'blur' }
                ]
            }
        }
    },
    methods: {
        handleSort(column, prop, order) {
            this.$http.post('priority/listPage', {
                pageSize: this.pageSize,
                pageNum: this.pageNum,
                sort: { [prop]: order },
                param: {
                    name: this.name,
                    remark: this.remark,
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
        del(id) {
            this.$http.get('priority/delete?id=' + id).then(res => res.data).then(res => {
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
                this.form.id = row.id;
                this.form.name = row.name;
                this.form.remark = row.remark;
                this.form.createtime = row.createtime;
            });
        },
        doSave() {
            this.$http.post('priority/save', this.form).then(res => res.data).then(res => {
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
            this.$http.post('priority/update', this.form).then(res => res.data).then(res => {
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
        resetForm() {
            this.$refs.form.resetFields();
        },
        add() {
            this.form.id = '';
            this.centerDialogVisible = true;
            this.$nextTick(() => {
                this.resetForm();
            });
        },
        resetParam() {
            this.name = '';
            this.remark = '';
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
        loadPost() {
            this.$http.post('priority/listPage', {
                pageSize: this.pageSize,
                pageNum: this.pageNum,
                param: {
                    name: this.name,
                    remark: this.remark,
                }
            }).then(res => res.data).then(res => {
                if (res.code == 200) {
                    console.log(res);

                    this.tableData = res.data;
                    this.total = res.total;
                } else {
                    this.$message.error('获取数据失败');
                }
            }).finally(() => {
                this.loading = false;
            });
        },
    },
    beforeMount() {
        this.loadPost();
    }
}
</script>

<style></style>
