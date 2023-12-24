<template>
    <el-calendar>
        <template #date-cell="{ data }">
        <p :class="data.isSelected ? 'is-selected' : ''">
            {{ data.day.split('-').slice(1).join('-') }}
            {{ data.isSelected ? '✔️' : '' }}
        </p>
        </template>
    </el-calendar>
    <!-- <el-calendar :range="[new Date(2019, 2, 4), new Date(2019, 2, 24)]" /> -->
</template>

<script>
export default {
    name:"calendarS"
}
</script>

<style scoped>
.is-selected {
  color: #1989fa;
}
</style>