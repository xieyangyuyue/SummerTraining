<template>
  <div>
    <!-- 列表查询 -->
    <div style="margin-bottom: 5px;">
      <el-input v-model="name" placeholder="请输入司机姓名" suffix-icon="el-icon-search" style="width: 200px;"
        @change="loadPost"></el-input>
      <el-input v-model="license" placeholder="请输入驾驶证号码" suffix-icon="el-icon-search" style="width: 200px;"
        @change="loadPost"></el-input>
      <el-button type="primary" @click="loadPost">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>
      <el-button type="danger" @click="add">新增</el-button>
    </div>

    <el-table :data="tableData" :header-cell-style="{ background: '#f2f5fc', color: '#555555' }" border
      v-loading="loading">
      <el-table-column prop="id" label="Id" width="60" />
      <el-table-column prop="name" label="司机姓名" width="160" sortable />
      <el-table-column prop="licensenumber" label="驾驶证号码" />
      <el-table-column prop="phonenumber" label="电话" />
      <el-table-column prop="licenseexpirydate" label="驾驶证过期日期" sortable />
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

    <el-dialog v-model="dialogVisible" title="司机信息" width="500" center @close="resetForm">
      <el-form ref="form" :model="form" :rules="rules" label-width="auto">
        <el-form-item label="司机姓名" style="width: 60%;" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="驾驶证号码" style="width: 60%;" prop="licensenumber">
          <el-input v-model="form.licensenumber" />
        </el-form-item>
        <el-form-item label="电话" style="width: 60%;" prop="phonenumber">
          <el-input v-model="form.phonenumber" />
        </el-form-item>
        <el-form-item label="驾驶证过期日期" style="width: 60%;" prop="licenseexpirydate">
          <el-date-picker v-model="form.licenseexpirydate" type="datetime" format="yyyy-MM-dd HH:mm:ss" />
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
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      name: '',
      license: '',
      loading: false,
      dialogVisible: false,
      form: {
        id: null,
        name: '',
        licensenumber: '',
        phonenumber: '',
        licenseexpirydate: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入司机姓名', trigger: 'blur' }
        ],
        licensenumber: [
          { required: true, message: '请输入驾驶证号码', trigger: 'blur' },
          { pattern: /^1[3-9]\d{1}$/, message: '驾驶证号码格式不正确', trigger: 'blur' }
        ],
        phonenumber: [
          { required: true, message: '请输入电话', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
        ],
        licenseexpirydate: [
          { required: true, message: '请选择驾驶证过期日期', trigger: 'change' }
        ]
      }
    };
  },
  methods: {
    loadPost() {
      this.loading = true;
      this.$http.post('/driver/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          name: this.name,
          license: this.license
        }
      }).then(response => {
        const { code, data, total } = response.data;
        if (code === 200) {
          this.tableData = data;
          this.total = total;
        } else {
          this.$message.error('获取数据失败');
        }
      }).catch(error => {
        console.error('请求失败', error);
        this.$message.error('请求失败，请检查网络或服务器状态');
      }).finally(() => {
        this.loading = false;
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
      this.$refs.form.validate(valid => {
        if (valid) {
          const url = this.form.id ? '/driver/update' : '/driver/save';
          this.$http.post(url, this.form).then(response => {
            if (response.data.code === 200) {
              this.$message.success('操作成功');
              this.dialogVisible = false;
              this.loadPost();
            } else {
              this.$message.error('操作失败');
            }
          }).catch(error => {
            console.error('请求失败', error);
            this.$message.error('请求失败，请检查网络或服务器状态');
          });
        } else {
          this.$message.error('请填写完整数据');
        }
      });
    },
    del(id) {
      this.$http.get('/driver/delete', { params: { id } }).then(response => {
        if (response.data.code === 200) {
          this.$message.success('删除成功');
          this.loadPost();
        } else {
          this.$message.error('删除失败');
        }
      }).catch(error => {
        console.error('请求失败', error);
        this.$message.error('请求失败，请检查网络或服务器状态');
      });
    },
    resetForm() {
      if (this.$refs.form) {
        this.$refs.form.resetFields();
      }
      this.form = {
        id: null,
        name: '',
        licensenumber: '',
        phonenumber: '',
        licenseexpirydate: ''
      };
    },
    resetParam() {
      this.name = '';
      this.license = '';
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
