<template>
  <div class="main-content">
    <div style="width: 70%; margin: 20px auto">
      <el-carousel height="500px" style="border-radius: 10px">
        <el-carousel-item v-for="item in carousel" :key="item">
          <img :src="item" alt="" style="width: 100%; height: 100%; border-radius: 10px">
        </el-carousel-item>
      </el-carousel>
      <div>
        <div style="margin: 20px 0 0 0; width: 130px; background-color: #f16f44; height: 30px; line-height: 30px; text-align: center; font-size: 18px; color: white; font-weight: bold; border-radius: 20px">优秀社团</div>
        <div style="margin-top: 15px">
          <el-row :gutter="20">
            <el-col :span="5" v-for="item in departmentData">
              <img :src="item.img" alt="" style="border:1px solid #cccccc; width: 100%; height: 196px; border-radius: 50%" @click="navTo('/front/departmentDetail?id=' + item.id)">
              <div style="text-align: center; margin-top: 10px; font-size: 16px">{{ item.name }}</div>
            </el-col>
          </el-row>
        </div>
      </div>

    </div>

  </div>
</template>

<script>


import information from "@/views/manager/Information.vue";

export default {
  computed: {
    information() {
      return information
    }
  },

  data() {
    return {
      carousel: [
        require('@/assets/imgs/bg.jpg'),
        require('@/assets/imgs/bg1.jpg'),
        require('@/assets/imgs/《dookie》.jpg'),
      ],
      departmentData: [],
      activityData: [],
      ActivityPageNum: 1,
      ActivityPageSize: 3,
      ActivityTotal: 0,
      informationData: [],
      InformationPageNum: 1,
      InformationPageSize: 3,
      InformationTotal: 0,
    }
  },
  mounted() {
    this.loadDepartment()
    this.loadActivity()
    this.loadInformation()
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    loadInformation(pageNum) {
      if (pageNum) this.InformationPageNum = pageNum
      this.$request.get('/information/selectPage2', {
        params: {
          pageNum: this.InformationPageNum,
          pageSize: this.InformationPageSize ,
        }
      }).then(res => {
        if (res.code === '200') {
          this.informationData = res.data?.list
          this.InformationTotal = res.data?.total
        } else {
          this.$message.error(res.msg)
        }

      })
    },
    loadActivity(pageNum) {
      if (pageNum) this.ActivityPageNum = pageNum
      this.$request.get('/activity/selectPage2', {
        params: {
          pageNum: this.ActivityPageNum,
          pageSize: this.ActivityPageSize ,
        }
      }).then(res => {
        if (res.code === '200') {
          this.activityData = res.data?.list
          this.ActivityTotal = res.data?.total
        } else {
          this.$message.error(res.msg)
        }

      })
    },
    loadDepartment() {
      this.$request.get('/department/selectAll').then(res => {
        if (res.code === '200') {
          this.departmentData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    navTo(url) {
      location.href = url
    },
    handleActivityCurrentChange(pageNum) {
      this.loadActivity(pageNum)
    },
    handleInformationCurrentChange(pageNum) {
      this.loadInformation(pageNum)
    },
  }
}
</script>
<style scoped>
.el-col-5{
  width: 20%;
  max-width: 20%;
  padding: 10px 10px;
}
.clamp-text {
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
}
</style>