<template>
  <div class="about-us">
    <canvas ref="starfield"></canvas>
    <div class="about-us-content">
      <h1 class="title">关于我们</h1>
      <p class="description">
        欢迎来到胜光运输管理系统！我们致力于提供高效、可靠的运输管理服务，为客户提供最佳的物流解决方案。
        <br /><br />
        我们的团队由经验丰富的专业人士组成，他们对运输和物流领域充满热情，致力于优化每一个运输环节，
        确保您的货物安全、准时到达目的地。
        <br /><br />
        如果您有任何问题或需要进一步的信息，请随时联系我们。我们期待为您提供优质的服务！
      </p>
      <div class="contact-info">
        <h2>联系我们</h2>
        <p>
          <strong>邮箱：</strong> sanxiadaxue
        </p>
        <p>
          <strong>电话：</strong> 0717
        </p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AboutUs',
  mounted() {
    this.createStarfield();
  },
  methods: {
    createStarfield() {
      const canvas = this.$refs.starfield;
      const ctx = canvas.getContext('2d');
      const stars = [];
      const numStars = 100;

      // Set canvas dimensions
      canvas.width = window.innerWidth;
      canvas.height = window.innerHeight;

      function createStar() {
        return {
          x: Math.random() * canvas.width,
          y: Math.random() * canvas.height,
          radius: Math.random() * 2 + 1,
          alpha: Math.random() * 0.5 + 0.5
        };
      }

      function drawStars() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        ctx.fillStyle = 'white';
        ctx.globalAlpha = 0.8;

        stars.forEach(star => {
          ctx.beginPath();
          ctx.arc(star.x, star.y, star.radius, 0, Math.PI * 2);
          ctx.fill();
        });
      }

      function updateStars() {
        stars.forEach(star => {
          star.x += (Math.random() - 0.5) * 0.5;
          star.y += (Math.random() - 0.5) * 0.5;

          if (star.x < 0) star.x = canvas.width;
          if (star.x > canvas.width) star.x = 0;
          if (star.y < 0) star.y = canvas.height;
          if (star.y > canvas.height) star.y = 0;
        });
      }

      function animate() {
        drawStars();
        updateStars();
        requestAnimationFrame(animate);
      }

      for (let i = 0; i < numStars; i++) {
        stars.push(createStar());
      }

      animate();
    }
  }
}
</script>

<style scoped>
.about-us {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  padding: 20px;
  overflow: hidden;
}

canvas {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 0;
}

.about-us-content {
  position: relative;
  max-width: 700px;
  width: 100%;
  background-color: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  z-index: 1;
}

.title {
  font-size: 32px;
  margin-bottom: 20px;
  color: #333;
  text-align: center;
}

.description {
  font-size: 16px;
  line-height: 1.6;
  color: #666;
}

.contact-info {
  margin-top: 30px;
}

.contact-info h2 {
  font-size: 24px;
  margin-bottom: 10px;
  color: #333;
}

.contact-info p {
  font-size: 16px;
  color: #666;
}
</style>
