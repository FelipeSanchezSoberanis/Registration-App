<script setup lang="ts">
const props = defineProps({ condition: Boolean });
</script>

<template>
  <div id="popup" v-if="props.condition" class="popupBackground">
    <div class="card p-5">
      <slot />
    </div>
  </div>
</template>

<style scoped lang="scss">
#popup {
  width: 100%;
  height: 100%;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  background-color: rgba(0, 0, 0, 0.5);

  .card {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    box-shadow: 10px 10px 5px black;
  }
}
</style>
