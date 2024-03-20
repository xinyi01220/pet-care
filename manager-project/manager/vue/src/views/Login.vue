<template>
  <div class="container">
    <div style="width: 400px; padding: 30px; background-color: white; border-radius: 5px;">
      <div style="text-align: center; font-size: 20px; margin-bottom: 20px; color: #333">欢迎使用Petcare</div>
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username">
          <el-input prefix-icon="el-icon-user" placeholder="请输入账号" v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" placeholder="请输入密码" show-password  v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item prop="role">
          <el-select v-model="form.role" placeholder="请选择角色" style="width: 100%">
            <el-option label="管理员" value="ADMIN"></el-option>
            <el-option label="用户" value="USER"></el-option>
          </el-select>
        </el-form-item>
<!--        <el-form-item prop="code">-->
<!--          <div style="display: flex">-->
<!--            <el-input style="flex: 1" size="medium" v-model="code"></el-input>-->
<!--            <Identify :identifyCode="identifyCode" @click.native="refreshCode" />-->
<!--          </div>-->
<!--        </el-form-item>-->
        <el-form-item>
          <el-button style="width: 100%; background-color: #2c334c; border-color: #2c334c; color: white" @click="login">登 录</el-button>
        </el-form-item>
        <div style="display: flex; align-items: center">
          <div style="flex: 1"></div>
          <div style="flex: 1; text-align: right">
            还没有账号？请 <a href="/register">注册</a>
          </div>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import Identify from "@/components/Identify";

export default {
  name: "Login",
  // components: {
  //   Identify
  // },
  data() {
    return {
      // form: { role: 'ADMIN' },// 默认是管理员
      form: { },
      rules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ]
      },
      // code: '',   // 表单绑定的验证码
      // // 图片验证码
      // identifyCode: '',
      // // 验证码规则
      // identifyCodes: '123456789ABCDEFGHGKMNPQRSTUVWXY',
    }
  },
  mounted() {
    this.makeCode(this.identifyCodes, 4)
    this.refreshCode()
  },
  methods: {
    // // 切换验证码
    // refreshCode() {
    //   this.identifyCode = ''
    //   this.makeCode(this.identifyCodes, 4)
    // },
    // // 生成随机验证码
    // makeCode(o, l) {
    //   for (let i = 0; i < l; i++) {
    //     this.identifyCode += this.identifyCodes[Math.floor(Math.random() * (this.identifyCodes.length))]
    //   }
    // },
    login() {
      // if (!this.code) {
      //   this.$message.warning('请输入验证码')
      //   this.refreshCode()
      //   return
      // }
      // if (this.code.toLowerCase() !== this.identifyCode.toLowerCase()) {
      //   this.$message.warning('验证码错误')
      //   this.refreshCode()
      //   return
      // }
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          // 验证通过
          this.$request.post('/login', this.form).then(res => {
            if (res.code === '200') {
              localStorage.setItem("xm-user", JSON.stringify(res.data))  // 存储用户数据
            if(res.data.level==='学生'){
              location.href = '/front/home'
            }
            else{
              this.$router.push('/')  // 跳转主页
            }

              this.$message.success('登录成功')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.container {
  height: 100vh;
  overflow: hidden;
  background-image: url("@/assets/imgs/《dookie》.jpg");
  background-size: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #666;
}
a {
  color: #2a60c9;
}
</style>