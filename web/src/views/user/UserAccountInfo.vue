<template>
    <div class="content-field">
        <div class="card">
            <div class="card-body">
                <slot>
                    404 This page could not be found.
                </slot>
            </div>
        </div>
    </div>
</template>

<script>
export default {
  data() {
    return {
    }
  },
  components:{
    }
}
</script>

<style scoped>
.content-field{
    background-image: url("@/assets/images/background2.jpg");
    background-size: cover;
    min-height:100vh;
    height: 100%;
    width: 100%; 
    position: fixed;
}
div.card{
    margin-top: 20px;
    text-align: center;
    margin-left: 50px;
    margin-right: 50px;
}
</style>