<template>
  <div class="main-content">
    <div style="width: 50%; margin: 20px auto">
      <div style="color: #333333; font-size: 20px; font-weight: 700">{{ departmentData.name }}：社团介绍，欢迎你的加入！
        <el-button type="primary" @click="init">申请加入</el-button>
      </div>
      <div style="margin-top: 10px; color: #767474">发布时间：{{ departmentData.time }}</div>
      <div style="margin-top: 30px" v-html="departmentData.description"></div>
    </div>
    <el-dialog title="填写信息" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="description" label="申请说明">
          <el-input v-model="description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

export default {

  data() {
    let departmentId = this.$route.query.id
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      departmentData: {},
      departmentId: departmentId,
      fromVisible: false,
      description: null,

    }
  },
  mounted() {
    this.loadDepartment()
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    loadDepartment() {
      this.$request.get('/department/selectById/' + this.departmentId).then(res => {
        if (res.code === '200') {
          this.departmentData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    init(){
      this.fromVisible = true

    },
    save(){
      let data = {
        departmentId: this.departmentId,
        userId: this.user.id,
        description: this.description
      }
      this.$request.post('/apply/add', data).then(res => {
        if (res.code === '200') {
          this.$message.success('申请成功,等待社长审核，您可以在申请的社团查看审核进度！')
          this.fromVisible = false
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  }
}
</script>