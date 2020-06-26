<template>
  <div class="logo-wrapper">
    <img src="/static/images/logo.png" class="logo">
    <div class="tagline">Sckroll's kanban board project</div>
  </div>
</template>

<script>
export default {
  name: 'Logo'
}
</script>

<style lang="scss" scoped>
.logo-wrapper {
  text-align: center;
  margin-bottom: 40px;

  .logo {
    max-width: 300px;
    margin: 0 auto;
  }

  .tagline {
    line-height: 180%;
    color: #666;
  }
}
</style>
