<template>
  <div id="app">
    <el-input v-model="name" placeholder="请输入收件人姓名" suffix-icon="el-icon-search" style="width: 200px;"
      @change="loadPost"></el-input>
    <el-button type="primary" @click="loadPost">查询</el-button>
    <el-button type="success" @click="resetParam">重置</el-button>
    <el-button type="danger" @click="add">新增</el-button>
    <el-button type="info" @click="conformDelivery" v-show="user.roleId === 2">确认订单</el-button>
    <el-table :data="tableData" :header-cell-style="{ background: '#f2f5fc', color: '#555555' }" border
      highlight-current-row @current-change="selectCurrentChange">
      <el-table-column prop="id" label="Id" width="60" />
      <el-table-column prop="recipientname" label="收件人" width="160" />
      <el-table-column prop="address" label="地址" />
      <el-table-column prop="deliverydate" label="配送日期" />
      <el-table-column prop="status" label="状态">
        <!-- 插槽default
                 自定义列的内容-->
        <template #default="scope">
          <!-- el-tag用于标记和选择 -->
          <!-- type	Tag 的类型
                 disable-transitions	是否禁用渐变动画	false-->
          <!-- :type展示图标形状颜色，差值表达式展示文字信息 -->
          <el-tag :type="scope.row.status === 0 ? 'primary' : 'success'" disable-transition>{{ scope.row.status ===
            0 ?
            '运输中' :
            '已到达'
            }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="driver" label="司机" width="120" />
      <el-table-column prop="operate" label="操作" width="165">
        <template #default="scope">
          <el-button size="small" type="primary" @click="edit(scope.row)">编辑</el-button>
          <el-popconfirm title="确定删除吗?" @confirm="del(scope.row.id)">
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

    <el-dialog v-model="dialogVisible" title="配送信息" width="500" center @close="resetForm">
      <el-form ref="form" :model="form" :rules="rules" label-width="auto">
        <el-form-item label="收件人" style="width: 60%;" prop="recipientname">
          <el-input v-model="form.recipientname" />
        </el-form-item>
        <el-form-item label="地址" style="width: 60%;" prop="address">
          <el-select v-model="form.address" placeholder="请选择地址">
            <el-option v-for="option in addressOptions" :key="option.value" :label="option.label" :value="option.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="配送日期" style="width: 60%;" prop="deliverydate">
          <el-date-picker v-model="form.deliverydate" type="datetime" format="yyyy-MM-dd HH:mm:ss" />
        </el-form-item>
        <el-form-item label="状态" style="width: 60%;" prop="status">
          <el-input v-model="form.status" />
        </el-form-item>
        <el-form-item label="司机" style="width: 60%;" prop="driver">
          <el-input v-model="form.driver" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>




<script>
export default {
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem('LoginUser')),
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      name: '',
      dialogVisible: false,
      currentRow: {},
      form: {
        id: '',
        recipientname: '',
        address: '',
        deliverydate: '',
        status: '',
        driver: ''
      },
      addressOptions: [
        { value: 'address1', label: '地址1' },
        { value: 'address2', label: '地址2' },
        { value: 'address3', label: '地址3' },
        // Add more address options here
      ],
      rules: {
        recipientname: [
          { required: true, message: '请输入收件人姓名', trigger: 'blur' }
        ],
        address: [
          { required: true, message: '请选择地址', trigger: 'change' }
        ],
        deliverydate: [
          { required: true, message: '请选择配送日期', trigger: 'change' }
        ],
        status: [
          { required: true, message: '请输入状态', trigger: 'blur' }
        ],
        driver: [
          { required: true, message: '请输入司机信息', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {

    //确认订单
    conformDelivery() {
      if (!this.currentRow.id) {
        this.$message({
          message: '请选择记录',
          type: 'success'
        });
        return;
      }
      this.form.id = this.currentRow.id
      this.form.recipientname = this.currentRow.recipientname
      this.form.address = this.currentRow.address
      this.form.deliverydate = this.currentRow.deliverydate
      this.form.driver = this.currentRow.driver
      this.form.status = 1;
      this.$http.post('delivery/update', this.form).then(res => res.data).then(res => {
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
    selectCurrentChange(val) {
      this.currentRow = val;
    },
    loadPost() {
      this.$http.post('/delivery/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          name: this.name + '',
          roleId: this.user.roleId + '',
          username: this.user.name + ''
        }
      }).then(response => {
        const { code, data, total } = response.data;
        if (code === 200) {
          this.tableData = data;
          this.total = total;
        } else {
          this.$message.error('获取数据失败');
        }
      });
    },
    add() {
      this.resetForm();
      this.dialogVisible = true;
    },
    edit(row) {
      this.form = { ...row };
      this.dialogVisible = true;
    },
    save() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          const url = this.form.id ? '/delivery/update' : '/delivery/save';
          this.$http.post(url, this.form).then(response => {
            if (response.data.code === 200) {
              this.$message.success('操作成功');
              this.dialogVisible = false;
              this.loadPost();
            } else {
              this.$message.error('操作失败');
            }
          });
        } else {
          this.$message.error('请填写完整数据');
        }
      });
    },
    del(id) {
      this.$http.get('/delivery/delete', { params: { id } }).then(response => {
        if (response.data.code === 200) {
          this.$message.success('删除成功');
          this.loadPost();
        } else {
          this.$message.error('删除失败');
        }
      });
    },
    resetParam() {
      this.name = '';
      this.loadPost();
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.pageNum = 1;
      this.loadPost();
    },
    handleCurrentChange(val) {
      this.pageNum = val;
      this.loadPost();
    },
    resetForm() {
      this.$nextTick(() => {
        if (this.$refs.form) {
          this.$refs.form.resetFields();
        }
        this.form = {
          id: null,
          recipientname: '',
          address: '',
          deliverydate: '',
          status: '',
          driver: ''
        };
      });
    }
  },
  mounted() {
    this.loadPost();
  }
};
</script>
<style>
.dialog-footer {
  text-align: right;
}
</style>
