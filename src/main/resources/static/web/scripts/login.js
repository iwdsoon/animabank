Vue.createApp({
    
    data() {
      return {
        passwordType: "password",
        loginEmail: "",
        loginPassword: "",
        regFirstName: "",
        regLastName: "",
        regEmail: "",
        regPassword: "",
        errorActive: false,
        errorMessage: null,
        }
      },
  
    created(){

    },
  
    methods: {

      toggleType() {
        this.passwordType = this.passwordType === "password" ? "text" : "password";
      },
    
      toggleClass(){
        const signup = document.getElementById("sign-up"),
        signin = document.getElementById("sign-in"),
        loginin = document.getElementById("login-in"),
        loginup = document.getElementById("login-up")
    
          signup.addEventListener("click", () => {
          loginin.classList.remove("block")
          loginup.classList.remove("none")
          loginin.classList.add("none")
          loginup.classList.add("block")
          })
          signin.addEventListener("click", () => {
          loginin.classList.remove("none")
          loginup.classList.remove("block")
          loginin.classList.add("block")
          loginup.classList.add("none")
          })
      },

      signIn() {

        axios.post('/api/login', `email=${this.loginEmail}&password=${this.loginPassword}`,
        {headers:
          {'content-type':'application/x-www-form-urlencoded'}}
          ).then(() => window.location.replace("/web/dashboard.html")
          ).catch(error => console.log(error))
      },

      signUp(){

        axios.post('/api/clients',`firstName=${this.regFirstName}&lastName=${this.regLastName}&email=${this.regEmail}&password=${this.regPassword}`,
        {headers:
          {'content-type':'application/x-www-form-urlencoded'}}
          ).then( () => {
            this.loginEmail = this.regEmail
            this.loginPassword = this.regPassword
            this.signIn()
          }).catch(error =>{
            this.errorActive = true
            this.errorMessage = (error.response.data)})
    }
  },

    computed: {

  }
    
  }).mount('#app')