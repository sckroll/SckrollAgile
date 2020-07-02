<template>
  <div>

  </div>
</template>

<script>
export default {
  name: 'PageHeader',
  created() {
    this.$store.dispatch('getMyData')
  },
}
</script>

<style>

</style>
