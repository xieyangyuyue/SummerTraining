<template>
    <div>
        <!-- 列表查询 -->
        <!-- Input 输入框
         model-value / v-model	绑定值 
         v-model="name" v-model="sex"监控输入框
         输入传入data return 再传入loadpost中的param
         @change  enter键触发-->
        <div style="margin-bottom: 5px;">
            <el-input v-model="name" placeholder="请输入物品名" suffix-icon="Search" style="width: 200px;"
                @change="loadPost"></el-input>

            <el-select v-model="priority" placeholder="请选择公司" style="width: 240px;margin-left: 5px;"> <el-option
                    v-for="item in priorityData" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>

            <el-select v-model="trainingtype" placeholder="请选择运输方式" style="width: 240px;margin-left: 5px;"> <el-option
                    v-for="item in trainingtypeData" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>

            <el-button type="primary" style="margin-left: 5px ;" @click="loadPost">查询</el-button>
            <el-button type="success" @click="resetParam">重置</el-button>
        </div>
        <el-table :data="tableData" :header-cell-style="{ background: '#f2f5fc', color: '#555555' }" border>
            <el-table-column prop="id" label="Id" width="60" />
            <el-table-column prop="goodsname" label="物品名" width="120" />
            <el-table-column prop="priorityname" label="公司名" width="160" />
            <el-table-column prop="trainingtypename" label="运输方式" width="160" />

            <el-table-column prop="username" label="申请人" width="160" />
            <el-table-column prop="adminname" label="操作人" width="160" />
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
            <el-table-column prop="createtime" label="操作时间" width="160" sortable />
            <el-table-column prop="remark" label="备注" width="160" />
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
    </div>
</template>


<script>
export default {
    name: "RecordManage",

    data() {

        return {
            user: JSON.parse(sessionStorage.getItem('LoginUser')),
            tableData: [],
            priorityData: [],
            trainingtypeData: [],
            pageSize: 10,
            pageNum: 1,
            total: 0,
            name: '',
            priority: '',
            trainingtype: '',
            centerDialogVisible: false,

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
        // 重置
        resetParam() {
            this.name = ''
            this.priority = ''
            this.trainingtype = ''
        },
        //对话框关闭判定
        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => { });
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
            this.pageNum = 1
            this.pageSize = val
            this.loadPost()
        },


        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            this.pageNum = val
            this.loadPost()
        },

        // post请求需要传入json的表单数据
        loadPost() {
            //axios实现post请求
            //实现分页查询
            this.$http.post('record/listPageSelf',
                // 传入json形式的请求体 分页传入条数和页数
                {
                    pageSize: this.pageSize,
                    pageNum: this.pageNum,
                    //将前端监控的值传给param
                    param: {
                        name: this.name,
                        priority: this.priority + '',
                        trainingtype: this.trainingtype + '',
                        roleId: this.user.roleId + '',
                        userid: this.user.id + ''
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
    }
}
</script>

<style></style>