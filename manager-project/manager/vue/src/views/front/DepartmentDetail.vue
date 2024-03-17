<template>
  <div class="main-content">
    <div style="width: 50%; margin: 20px auto">
      <div style="color: #333333; font-size: 20px; font-weight: 700">{{ departmentData.name }}：社团介绍，欢迎你的加入！
        <el-button type="primary">申请加入</el-button>
      </div>
      <div style="margin-top: 10px; color: #767474">发布时间：{{ departmentData.time }}</div>
      <div style="margin-top: 30px" v-html="departmentData.description"></div>
    </div>
  </div>
</template>

<script>

export default {

  data() {
    let departmentId = this.$route.query.id
    return {
      departmentData: {},
      departmentId: departmentId
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
    }
  }
}
</script>