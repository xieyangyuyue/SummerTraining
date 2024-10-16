<template>
    <div>
        <!-- 列表查询 -->
        <!-- Input 输入框
         model-value / v-model	绑定值 
         v-model="name" v-model="sex"监控输入框
         输入传入data return 再传入loadpost中的param
         @change  enter键触发-->
        <div style="margin-bottom: 5px;">
            <el-input v-model="name" placeholder="请输入物品名" suffix-icon="Search" style="width: 150px;"
                @change="loadPost"></el-input>

            <el-select v-model="priority" placeholder="请选择公司" style="width: 150px;margin-left: 5px;"> <el-option
                    v-for="item in priorityData" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>

            <el-select v-model="trainingtype" placeholder="请选择运输方式" style="width: 150px;margin-left: 5px;"> <el-option
                    v-for="item in trainingtypeData" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
            <!-- Select 选择器​
            当选项过多时，使用下拉菜单展示并选择内容。 -->
            <el-select v-model="deliver" filterable placeholder="请选择是否发货" suffix-icon="Goods"
                style="width: 150px;margin-left: 5px ;">
                <el-option v-for="item in delivers" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
            </el-select>
            <el-select v-model="level" placeholder="请选择状态" style="width: 150px;margin-left: 5px;"> <el-option
                    v-for="item in levelData" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
            <!-- 调用loadPost查询  
            传入参数根据v-model="name" v-model="sex"中的name sex返回后端进行 -->
            <el-button type="primary" style="margin-left: 5px ;" @click="loadPost">查询</el-button>
            <el-button type="success" @click="resetParam">重置</el-button>
            <!-- add函数只是激发dialog对话框 展示新增表单 -->
            <el-button type="danger" @click="add">下单</el-button>
            <el-button type="info" @click="inGoods" v-show="user.roleId !== 2">发货</el-button>
        </div>

        <!-- data数据来源于tableData 
     header-cell-style	 设置背景表头单元格的 style 的回调方法，
     也可以使用一个固定的 Object 为所有表头单元格设置一样的 Style。
     border边框-->
        <el-table :data="tableData" :header-cell-style="{ background: '#f2f5fc', color: '#555555' }" border
            highlight-current-row @current-change="selectCurrentChange">
            <!-- prop后的值需跟数据库字段名匹配 -->
            <el-table-column prop="id" label="Id" width="60" />
            <el-table-column prop="name" label="物品名" width="160" />
            <el-table-column prop="priority" label="公司名" width="160" :formatter="formatPriority" />
            <el-table-column prop="trainingtype" label="运输方式" width="160" :formatter="formatTrainingtype" />
            <el-table-column prop="weight" label="发货" width="160">
                <!-- 插槽default 自定义列的内容-->
                <template #default="scope">
                    <!-- el-tag用于标记和选择 -->
                    <!-- type	Tag 的类型 disable-transitions	是否禁用渐变动画	false-->
                    <!-- :type展示图标形状颜色，差值表达式展示文字信息 -->
                    <el-tag :type="scope.row.weight === 1 ? 'primary' : 'success'" disable-transition>{{
                        scope.row.weight
                            ===
                            1 ?
                            '发货' :
                            '暂存'
                    }}</el-tag>
                </template> </el-table-column>
            <el-table-column prop="remark" label="备注" width="160" sortable>
                <!-- 插槽default 自定义列的内容-->
                <template #default="scope">
                    <!-- el-tag用于标记和选择 -->
                    <!-- type	Tag 的类型 disable-transitions	是否禁用渐变动画	false-->
                    <!-- :type展示图标形状颜色，差值表达式展示文字信息 -->
                    <el-tag
                        :type="scope.row.remark === '1' ? 'primary' : (scope.row.remark === '2' ? 'success' : 'danger')"
                        disable-transition>{{
                            scope.row.remark === '1' ? '急速' : (scope.row.remark === '2' ? '平衡' : '稳重')
                        }}</el-tag>
                </template> </el-table-column>
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
                <el-form-item label="物品名" style="width: 60%;" prop="name">
                    <el-input v-model="form.name" />
                </el-form-item>
                <el-form-item label="公司名" style="width: 60%;" prop="priority">
                    <!-- <el-input v-model="form.priority" /> -->
                    <el-select v-model="form.priority" placeholder="请选择公司" style="width: 240px;margin-left: 5px;">
                        <el-option v-for="item in priorityData" :key="item.id" :label="item.name" :value="item.id" />
                    </el-select>
                </el-form-item>
                <el-form-item label="运输方式" style="width: 60%;" prop="trainingtype">
                    <!-- <el-input v-model="form.trainingtype" /> -->
                    <el-select v-model="form.trainingtype" placeholder="请选择运输方式" style="width: 240px;margin-left: 5px;">
                        <el-option v-for="item in trainingtypeData" :key="item.id" :label="item.name"
                            :value="item.id" />
                    </el-select>
                </el-form-item>
                <el-form-item label="发货" style="width: 60%;" prop="weight" readonly>
                    <!-- 单选框 -->
                    <el-radio-group v-model="form.weight">
                        <el-radio value="0">暂存</el-radio>
                        <el-radio value="1">发货</el-radio>
                    </el-radio-group>
                </el-form-item>

                <!--备注  -->
                <el-form-item label="备注" style="width: 60%;" prop="remark">
                    <el-select v-model="form.remark" placeholder="请选择状态" style="width: 150px;margin-left: 5px;">
                        <el-option v-for="item in levelData" :key="item.id" :label="item.name" :value="item.id" />
                    </el-select>
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

        <!-- Dialog对话框 -->
        <el-dialog v-model="inDialogVisible" title="提示" width="500" center :before-close="handleClose">
            <!-- 内层 -->
            <el-dialog width="30%" title="用户选择" v-model="innerVisible" append-to-body>
                <selectuser @doselectUser="doselectUser"></selectuser>
                <template #footer>
                    <div class="dialog-footer">
                        <el-button @click="innerVisible = false">取消</el-button>
                        <el-button type="primary" @click="confirmUser">
                            确定
                        </el-button>
                    </div>
                </template>
            </el-dialog>
            <!-- 表单 -->
            <el-form ref="form1" :rules="rules1" :model="form1" label-width="auto" style="max-width: 600px">


                <!-- 名字 -->
                <el-form-item label="物品名" style="width: 60%;" prop="name">
                    <el-input v-model="form1.name" readonly />
                </el-form-item>
                <!-- 申请人 -->
                <el-form-item label="申请人" style="width: 60%;" prop="username">
                    <el-input v-model="form1.username" readonly @click="selectUser" />
                </el-form-item>
                <el-form-item label="发货" style="width: 60%;" prop="weight">
                    <!-- 单选框 -->
                    <el-radio-group v-model="form1.weight">
                        <el-radio value="0">暂存</el-radio>
                        <el-radio value="1">发货</el-radio>
                    </el-radio-group>
                </el-form-item>

                <!--备注  -->
                <el-form-item label="备注" style="width: 60%;" prop="remark">
                    <el-input type="textarea" v-model="form1.remark" />
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="inDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="doInGoods">
                        确定
                    </el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<script>
import selectuser from '../user/selectuser.vue';
export default {
    name: "TrainingorderManage",
    components: { selectuser },
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
            user: JSON.parse(sessionStorage.getItem('LoginUser')),
            //列表数据,以数组形式接收
            tableData: [],
            priorityData: [],
            trainingtypeData: [],
            levelData: [],
            priority: '',
            trainingtype: '',
            deliver: '',
            delivers: [
                {
                    value: '0',
                    label: '暂存'
                }, {
                    value: '1',
                    label: '发货'
                }
            ],
            level: '',

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
            inDialogVisible: false,
            innerVisible: false,
            currentRow: {},
            tempUser: '',
            // 新增表单 form动态关联
            form: {
                id: '',
                name: '',
                priority: '',
                trainingtype: '',
                weight: '0',
                remark: '',

            },
            form1: {
                goods: '',
                userid: '6',
                adminid: '',
                weight: '0',
                remark: '',

            },

            //表单规则校验
            rules: {
                name: [
                    { required: true, message: '请输入物品', trigger: 'blur' },
                    { min: 3, max: 20, message: '长度在 3 到 20个字符', trigger: 'blur' },
                    { validator: checkDuplicate, trigger: 'blur' }
                ],
                priority: [
                    { required: true, message: '请选择运输公司', trigger: 'blur' },
                ],
                trainingtype: [
                    { required: true, message: '请选择运输方式', trigger: 'blur' },
                ],
                remark: [
                    { required: true, message: '请选择运输公司', trigger: 'blur' },
                ],
            },
            rules1: {

            },
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
        doselectUser(val) {
            this.tempUser = val
        },
        confirmUser() {
            this.form1.username = this.tempUser.name
            this.form1.userid = this.tempUser.id
            this.innerVisible = false
        },

        selectUser() {
            this.innerVisible = true
        },

        doInGoods() {
            this.$http.post('record/save', this.form1).then(res => res.data).then(res => {
                // console.log(res)
                if (res.code == 200) {
                    this.$message({
                        message: '操作成功!',
                        type: 'success'
                    });
                    this.form.weight = '0'
                    this.inDialogVisible = false
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
        //入库
        inGoods() {
            if (!this.currentRow.id) {
                this.$message({
                    message: '请选择记录',
                    type: 'success'
                });
                return;
            }
            this.inDialogVisible = true
            this.$nextTick(() => {
                this.resetInForm();

            })
            this.form1.name = this.currentRow.name
            this.form1.goods = this.currentRow.id
            this.form1.adminid = this.user.id
        },
        selectCurrentChange(val) {
            this.currentRow = val;
        },
        //重置表单
        resetInForm() {
            this.$refs.form1.resetFields();
        },


        formatPriority(row) {
            let temp = this.priorityData.find(item => {
                return item.id == row.priority
            })
            return temp && temp.name
        },
        formatTrainingtype(row) {
            let temp = this.trainingtypeData.find(item => {
                return item.id == row.trainingtype
            })
            return temp && temp.name
        },
        //删除
        del(id) {
            console.log(id);
            this.$http.get('trainingorder/delete?id=' + id).then(res => res.data).then(res => {
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
                this.form.priority = row.priority
                this.form.trainingtype = row.trainingtype
                this.form.weight = row.weight
                this.form.remark = row.remark
            })
        },
        //表单新增
        doSave() {
            this.$http.post('trainingorder/save', this.form).then(res => res.data).then(res => {
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
            this.$http.post('trainingorder/update', this.form).then(res => res.data).then(res => {
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
            this.priority = ''
            this.trainingtype = ''
            this.deliver = ''
            this.level = ''
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
            this.$http.post('trainingorder/listPage',
                // 传入json形式的请求体 分页传入条数和页数
                {
                    pageSize: this.pageSize,
                    pageNum: this.pageNum,
                    //将前端监控的值传给param
                    param: {
                        name: this.name,
                        priority: this.priority + '',
                        trainingtype: this.trainingtype + '',
                        deliver: this.deliver + '',
                        remark: this.level + ''
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
        loadPriority() {
            this.$http.get('priority/list').then(res => res.data).then(res => {
                console.log(res)
                if (res.code == 200) {
                    this.priorityData = res.data
                }
                else {
                    alter('获取数据失败')
                }

            })
        },
        loadTrainingtype() {
            this.$http.get('trainingtype/list').then(res => res.data).then(res => {
                console.log(res)
                if (res.code == 200) {
                    this.trainingtypeData = res.data
                }
                else {
                    alter('获取数据失败')
                }

            })
        },
        loadLevel() {
            this.$http.get('level/list').then(res => res.data).then(res => {
                console.log(res)
                if (res.code == 200) {
                    this.levelData = res.data
                }
                else {
                    alter('获取数据失败')
                }

            })
        }
    },
    // 1、这是我们遇到的第一个生命周期函数,表示实例完全被创建出来之前,会执行它，
    // 注意:在 beforeCreate 生命周期函数执行的时候, data 和 methods 中的 数据都还没有初始化

    // 3、这是遇到的第三个生命周期函数,表示 模板已经在内存中编辑完成了,但是尚未把模板渲染到 页面中，
    // 在 beforeMount 执行的时候,页面中的元素,还没有被真正替换过来,只是之前写的一些模板字符串

    beforeMount() {
        // 调用loadGet loadPost
        // this.loadGet();
        this.loadPost();
        this.loadPriority();
        this.loadTrainingtype();
        this.loadLevel();
    }
}
</script>

<style></style>