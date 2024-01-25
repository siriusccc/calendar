<template>
  <div class="loader-container">
    <canvas ref="loaderCanvas" width="480px" height="480px"></canvas>
    <h1>小谭天下最美</h1>
  </div>
</template>
  
<script>
export default {
  mounted() {
    this.setupLoader();
  },
  methods: {
    setupLoader() {
      const canvas = this.$refs.loaderCanvas;
      const ctx = canvas.getContext('2d');
      const max_size = 24;
      const max_particles = 1500;
      const min_vel = 20;
      const max_generation_per_frame = 10;
      const particles = [];
      let last = Date.now();

      function isInsideHeart(x, y) {
        x = ((x - canvas.width / 2) / (canvas.width / 2)) * 3;
        y = ((y - canvas.height / 2) / (canvas.height / 2)) * -3;
        return Math.pow(x * x + y * y - 1, 3) - x * x * y * y * y < 0;
      }

      function random(size, freq) {
        let val = 0;
        let iter = freq;

        do {
          size /= iter;
          iter += freq;
          val += size * Math.random();
        } while (size >= 1);

        return val;
      }

      function Particle() {
        let x = canvas.width / 2;
        let y = canvas.height / 2;
        let size = ~~random(max_size, 2.4);
        let x_vel = ((max_size + min_vel) - size) / 2 - (Math.random() * ((max_size + min_vel) - size));
        let y_vel = ((max_size + min_vel) - size) / 2 - (Math.random() * ((max_size + min_vel) - size));
        let nx = x;
        let ny = y;
        let r, g, b, a = 0.05 * size;

        this.draw = function () {
          r = ~~(255 * (x / canvas.width));
          g = ~~(255 * (1 - (y / canvas.height)));
          b = ~~(255 - r);
          ctx.fillStyle = 'rgba(' + r + ',' + g + ',' + b + ',' + a + ')';
          ctx.beginPath();
          ctx.arc(x, y, size, 0, Math.PI * 2, true);
          ctx.closePath();
          ctx.fill();
        };

        this.move = function (dt) {
          nx += x_vel * dt;
          ny += y_vel * dt;
          if (!isInsideHeart(nx, ny)) {
            if (!isInsideHeart(nx, y)) {
              x_vel *= -1;
              return;
            }

            if (!isInsideHeart(x, ny)) {
              y_vel *= -1;
              return;
            }

            x_vel = -1 * y_vel;
            y_vel = -1 * x_vel;
            return;
          }

          x = nx;
          y = ny;
        };
      }

      function movementTick() {
        const len = particles.length;
        const dead = max_particles - len;
        for (let i = 0; i < dead && i < max_generation_per_frame; i++) {
          particles.push(new Particle());
        }

        const now = Date.now();
        let dt = last - now;
        dt /= 1000;
        last = now;
        particles.forEach(function (p) {
          p.move(dt);
        });
      }

      function tick() {
        ctx.clearRect(0, 0, canvas.width * 10, canvas.height * 10);
        particles.forEach(function (p) {
          p.draw();
        });

        requestAnimationFrame(tick);
      }

      setInterval(movementTick, 16);
      tick();
    },
  },
};
</script>
  
<style scoped>
.loader-container {
  position: fixed;
  top: 57px;
  left: 0;
  width: 100%;
  height: 100%;
  background: url('@/assets/images/background5.jpg');
  background-size: cover;
}

.loader-container h1 {
  position: absolute;
  top: 100px;
  left: 0px;
  text-align: center;
  width: 100%;
  top: 0px;
  line-height: 420px;
  font-size: 24px;
  color: rgba(0, 0, 0, 0.4);
  font-weight: 500;
}
canvas {
  position: absolute;
  top: 0%;
  left: 34.5%;
}
</style>
