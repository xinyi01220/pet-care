<template>
  <div style="margin: 5px auto; width: 60%">
    <article-list/>
  </div>
</template>

<script>
import ArticleList from "@/components/ArticleList";
export default {
  name: "Search",
  components: {ArticleList},
  data() {
    return {
      title: this.$route.query.title
    }
  },
  created() {

  },
  methods: {}
}
</script>

<style scoped>

</style>