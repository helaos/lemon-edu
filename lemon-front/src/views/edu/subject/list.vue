<template>
  <div class="app-container">
    <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:30px;" />

    <el-tree ref="tree2" :data="data2" :props="defaultProps" :filter-node-method="filterNode" class="filter-tree"
      default-expand-all />

  </div>
</template>

<script>
  import subject from '@/api/edu/subject';
  export default {

    data() {
      return {
        filterText: '',
        data2: [], // 返回的所有数据
        defaultProps: {
          children: 'children',
          label: 'title'
        }
      }
    },
    created() {
      this.getAllSubjectList();
    },
    watch: {
      filterText(val) {
        this.$refs.tree2.filter(val)
      }
    },
    methods: {
      filterNode(value, data) {
        if (!value) return true
        // 优化不区分大小写
        return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1
      },

      getAllSubjectList() {
        subject.getSubjectList()
          .then(response => {
            this.data2 = response.data.list;
          })
      }
    }
  }

</script>
