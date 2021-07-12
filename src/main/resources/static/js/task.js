new Vue({
  el: '#app',
  data() {
    return {
      isClicked: false,
    };
  },
  methods: {
    popUpAlert: function() {
      if (!alert('OKボタンを押してください。')) {
        this.isClicked = true;
      }
    }
  },
});