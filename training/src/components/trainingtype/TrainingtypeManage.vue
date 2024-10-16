<template>
    <div>
        <!-- 列表查询 -->
        <!-- Input 输入框
         model-value / v-model	绑定值 
         v-model="name" v-model="sex"监控输入框
         输入传入data return 再传入loadpost中的param
         @change  enter键触发-->
        <div style="margin-bottom: 5px;">
            <el-input v-model="name" placeholder="请输入运输方式" suffix-icon="Search" style="width: 200px;"
                @change="loadPost"></el-input>


            <!-- 调用loadPost查询  
            传入参数根据v-model="name" v-model="sex"中的name sex返回后端进行 -->
            <el-button type="primary" style="margin-left: 5px ;" @click="loadPost">查询</el-button>
            <el-button type="success" @click="resetParam">重置</el-button>
            <!-- add函数只是激发dialog对话框 展示新增表单 -->
            <el-button type="danger" @click="add">新增</el-button>
        </div>

        <!-- data数据来源于tableData 
     header-cell-style	 设置背景表头单元格的 style 的回调方法，
     也可以使用一个固定的 Object 为所有表头单元格设置一样的 Style。
     border边框-->
        <el-table :data="tableData" :header-cell-style="{ background: '#f2f5fc', color: '#555555' }" border>
            <!-- prop后的值需跟数据库字段名匹配 -->
            <el-table-column prop="id" label="Id" width="60" />
            <el-table-column prop="name" label="运输方式" width="160" />
            <el-table-column prop="remark" label="备注" />
            <el-table-column prop="operate" label="操作" width="165">


                <template #default="scope">
                    <!-- 插槽传递数据 
                     mod(scope.row)传递一行
                      @confirm="del(scope.row.id)"传递id-->
                    <!-- el-button按钮 -->
                    <el-button size="small" type="primary" @click="mod(scope.row)">编辑</el-button>
                    <!-- Popconfirm 气泡确认框 
                     confirm-button-text="确定" 确认文字
                     cancel-button-text="取消"  取消文字-->
                    <!-- 插槽reference	触发 Popconfirm 显示的 HTML 元素 -->
                    <el-popconfirm confirm-button-text="确定" cancel-button-text="取消" title="确定删除吗?"
                        @confirm="del(scope.row.id)">
                        <!-- 插槽reference	触发 Popconfirm 显示的 HTML 元素 -->
                        <template #reference>
                            <el-button size="small" type="danger">删除</el-button>
                        </template>
                    </el-popconfirm>
                </template>


            </el-table-column>
        </el-table>





        <!-- 分页处理 -->

        <!-- :current-page赋值 pageNum当前页数
         :page-size="pageSize" 每页条数
         @size-change="handleSizeChange"  点击事件改变每页条数
         @current-change="handleCurrentChange" 
        -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
            :page-sizes="[2, 5, 10, 20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>







        <!-- Dialog对话框 Dialog 弹出一个对话框
     model-value / v-model	是否显示 Dialog	boolean 
     centerDialogVisible  新增dialog是否可见-->
        <el-dialog v-model="centerDialogVisible" title="提示" width="500" center :before-close="handleClose">
            <!-- 新增表单 
             form动态关联
             model	表单数据对象	
             rules	表单验证规则
             Form 组件提供了表单验证的功能，
             只需为 rules 属性传入约定的验证规则，
             并将 form-Item 的 prop 属性设置为需要验证的特殊键值即可-->
            <!-- 表单 -->
            <el-form ref="form" :rules="rules" :model="form" label-width="auto" style="max-width: 600px">


                <!-- 名字 -->
                <el-form-item label="运输方式" style="width: 60%;" prop="name">
                    <el-input v-model="form.name" />
                </el-form-item>

                <!--备注  -->
                <el-form-item label="备注" style="width: 60%;" prop="remark">
                    <el-input type="textarea" v-model="form.remark" />
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="centerDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="save">
                        确定
                    </el-button>
                </div>
            </template>
        </el-dialog>


    </div>
</template>

<script>
export default {
    name: "TrainingtypeManage",
    data() {
        // 校验账号的唯一性 checkDuplicate 
        let checkDuplicate = (rule, value, callback) => {
            if (this.form.id) {
                return callback();
            }

            this.$http.get("/trainingtype/findByNo?name=" + this.form.name).then(res => res.data)
                .then(res => {
                    if (res.code != 200) {
                        callback();
                    }
                    else {
                        callback(new Error('运输方式已存在'));
                    }
                })
        };






        return {
            //列表数据,以数组形式接收
            tableData: [],
            //分页所需赋值数据 
            //pageSize  pageNum total先赋值默认数据
            pageSize: 10,
            pageNum: 1,
            total: 0,
            //input列表查询
            //绑定监控name sex
            name: '',

            //新增dialog是否可见
            centerDialogVisible: false,
            // 新增表单 form动态关联
            form: {
                id: '',
                name: '',
                remark: ''
            },

            //表单规则校验
            rules: {
                name: [
                    { required: true, message: '请输入运输方式', trigger: 'blur' },
                    { min: 3, max: 20, message: '长度在 3 到 20个字符', trigger: 'blur' },
                    { validator: checkDuplicate, trigger: 'blur' }
                ],
            }
        }
    },
    methods: {
        //删除
        del(id) {
            console.log(id);
            this.$http.get('trainingtype/delete?id=' + id).then(res => res.data).then(res => {
                if (res.code == 200) {
                    this.$message({
                        message: '操作成功!',
                        type: 'success'
                    });
                    this.loadPost()
                }
                else {
                    this.$message({
                        message: '操作失败!',
                        type: 'error'
                    });
                }
            })
        },
        //修改
        mod(row) {
            // console.log(row);
            //赋值到表单
            //展示
            this.centerDialogVisible = true
            // 执行异步
            this.$nextTick(() => {
                this.form.id = row.id
                this.form.name = row.name
                this.form.remark = row.remark
            })
        },
        //表单新增
        doSave() {
            this.$http.post('trainingtype/save', this.form).then(res => res.data).then(res => {
                // console.log(res)
                if (res.code == 200) {
                    this.$message({
                        message: '操作成功!',
                        type: 'success'
                    });
                    this.centerDialogVisible = false
                    this.loadPost()
                }
                else {
                    this.$message({
                        message: '操作失败!',
                        type: 'error'
                    });
                }
            })
        },
        //表单修改
        doMod() {
            this.$http.post('trainingtype/update', this.form).then(res => res.data).then(res => {
                console.log(res)
                if (res.code == 200) {
                    this.$message({
                        message: '操作成功!',
                        type: 'success'
                    });
                    this.centerDialogVisible = false
                    this.loadPost()
                }
                else {
                    this.$message({
                        message: '操作失败!',
                        type: 'error'
                    });
                }
            })
        },
        //表单提交
        save() {
            // 校验合法 表单数据合法才能提交请求
            this.$refs.form.validate((valid) => {
                if (valid) {
                    //存在修改，不存在新增
                    if (this.form.id) {
                        this.doMod()
                    }
                    else {
                        //新增表单提交后端
                        this.doSave()
                    }
                } else {
                    this.$message({
                        message: '校验失败 请填写完整数据',
                        type: 'error'
                    });
                    this.confirm_disable = false;
                    return false;
                }
            });



        },
        //重置表单
        resetForm() {
            this.$refs.form.resetFields();
        },
        //表单新增,
        add() {
            this.form.id = '',
                //展示dialog对话框
                this.centerDialogVisible = true
            this.$nextTick(() => {
                this.resetForm();
            })
        },
        // input查询框重置 将传入的值赋值为空
        resetParam() {
            this.name = ''
        },
        //关于分页函数 handleSizeChange，handleCurrentChange
        //改变每页条数，传值val，将pageSize改为val
        handleSizeChange(val) {
            //默认当前页为1
            this.pageNum = 1
            this.pageSize = val
            this.loadPost()
        },

        //改变当前页数，传值val，将pageNum改为val
        handleCurrentChange(val) {
            this.pageNum = val
            this.loadPost()
        },
        //打印所有信息(user)
        loadGet() {
            //axios实现get请求
            this.$http.get('trainingtype/list').then(res => res.data).then(res => {
                // console.log(res);
            })
        },
        // post请求需要传入json的表单数据
        loadPost() {
            //axios实现post请求
            //实现分页查询
            this.$http.post('trainingtype/listPage',
                // 传入json形式的请求体 分页传入条数和页数
                {
                    pageSize: this.pageSize,
                    pageNum: this.pageNum,
                    //将前端监控的值传给param
                    param: {
                        name: this.name,
                    }
                }).then(res => res.data).then(res => {
                    // res => res.data过滤后端返回数据包含code，msg，data等
                    console.log(res);
                    // res.code == 200表示后端返回的code数据为200,代表后端操作成功
                    if (res.code == 200) {
                        //tableData列表数据,以数组形式接收
                        //res.data从后端拿到data的数据，只包含data
                        //res.total将后端的条数赋值前端total
                        this.tableData = res.data
                        this.total = res.total
                    }
                    else {
                        alter('获取数据失败')
                    }
                })
        },
    },
    // 1、这是我们遇到的第一个生命周期函数,表示实例完全被创建出来之前,会执行它，
    // 注意:在 beforeCreate 生命周期函数执行的时候, data 和 methods 中的 数据都还没有初始化

    // 3、这是遇到的第三个生命周期函数,表示 模板已经在内存中编辑完成了,但是尚未把模板渲染到 页面中，
    // 在 beforeMount 执行的时候,页面中的元素,还没有被真正替换过来,只是之前写的一些模板字符串
    beforeMount() {
        // 调用loadGet loadPost
        // this.loadGet();
        this.loadPost();
    }
}
</script>

<style></style>