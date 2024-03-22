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
      <div style="display: flex; align-items: flex-start; grid-gap: 10px;margin-top: 20px">
        <div style="width: 150px" class="card">
          <div class="category-item" :class="{ 'category-item-active': item.name === current }"
               v-for="item in categoryList" :key="item.id" @click="selectCategory(item.name)">{{ item.name }}</div>
        </div>

        <div style="flex: 1;">

          <article-list :categoryName="current" ref="articleListRef" />

          <Footer />
        </div>
      </div>
      <div style="width: 260px">
        <div class="card" style="margin-bottom: 10px">
          <div style="font-size: 20px; font-weight: bold; margin-bottom: 10px">欢迎您！😊</div>
          <a href="/front/person"><div style="color: #666">写下博客记录美好的一天</div></a>
        </div>
      </div>

      <div class="card" style="margin-bottom: 10px">
        <div style="display: flex; align-items: center; padding-bottom: 10px; border-bottom: 1px solid #ddd">
          <div style="font-size: 20px; flex: 1">文章榜单</div>
          <div style="font-size: 12px; color: #666; cursor: pointer;" @click="refreshTop"><i class="el-icon-refresh"></i> 换一换</div>
        </div>
        <div>
          <div v-for="item in showList" :key="item.id" style="margin: 15px 0" class="line1">
            <a :href="'/front/articleDetail?articleId=' + item.id" target="_blank">
                <span style="width: 18px; display: inline-block; text-align: right; margin-right: 10px">
                  <span style="color: orangered" v-if="item.index === 1">{{ item.index }}</span>
                  <span style="color: goldenrod" v-else-if="item.index === 2">{{ item.index }}</span>
                  <span style="color: dodgerblue" v-else-if="item.index === 3">{{ item.index }}</span>
                  <span style="color: #666" v-else>{{ item.index }}</span>
                </span>
              <span style="color: #666;">{{ item.title }}</span>
            </a>
          </div>
        </div>
      </div>

      <div style="margin-bottom: 10px">
        <div v-for="item in topActivityList" :key="item.id" style="margin-bottom: 10px">
          <a :href="'/front/activityDetail?activityId=' + item.id" target="_blank"><img :src="item.cover" alt="" style="width: 100%; border-radius: 5px"></a>
        </div>
      </div>





    </div>


  </div>
</template>

<script>


import Footer from "@/components/Footer";
import ArticleList from "@/components/ArticleList";

export default {
  components: {
    ArticleList,
    Footer
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

      current: '全部文章',  //当前选中的分类名称
      categoryList: [],

      topList: [],
      showList: [],
      lastIndex: 0,
      topActivityList: []
    }
  },
  mounted() {
    this.loadDepartment()
    this.loadActivity()

    this.load()

    this.refreshTop()

    this.loadTopActivity()
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
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
    load() {
      // 请求分类的数据
      this.$request.get('/category/selectAll').then(res => {
        this.categoryList = res.data || []
        this.categoryList.unshift({ name: '全部文章' })
      })
    },
    refreshTop() {
      this.$request.get('/article/selectTop').then(res => {
        this.topList = res.data || []
        let i = 1
        this.topList.forEach(item => item.index = i++)

        // 0  5  0
        if (this.lastIndex === 20) {
          this.lastIndex = 0
        }
        this.showList = this.topList.slice(this.lastIndex, this.lastIndex+5)  // 0-5   5- 10  // 0-5
        this.lastIndex += 5  // 5  10  5
      })
    },
    loadTopActivity() {
      this.$request.get('/activity/selectTop').then(res => {
        this.topActivityList = res.data || []
      })
    },
    selectCategory(categoryName) {
      this.current = categoryName
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
.category-item {
  text-align: center;
  padding: 10px 0;
  font-size: 16px;
  cursor: pointer;
}
.category-item-active {
  background-color: #1890ff;
  color: #fff;
  border-radius: 5px;
}
</style>