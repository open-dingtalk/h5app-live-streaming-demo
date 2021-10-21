import './App.css';
import * as dd from 'dingtalk-jsapi';
import axios from 'axios';
import React from 'react';
import './App.css';


class App extends React.Component{
  constructor(props) {
    super(props);
    this.state = {
      //内网穿透工具介绍:
      // https://developers.dingtalk.com/document/resourcedownload/http-intranet-penetration?pnamespace=app
      domain: "",
      corpId: '',
      authCode: '',
      userId: '',
      userName: '',
      chatId:'',
      courseId:'',
      openConversationId:'',
      groupData:{
        name:"直播群"
      },
      courseData:{

      },
      liveData:{}
    }
  }
  render() {
    const corpId = window.location.href.split("=")[1]
    if(this.state.corpId === ''){
        sessionStorage.setItem("corpId", corpId);
    }
    if(this.state.userId === ''){
      this.login(corpId);
    }
    return (
        <div className="App">
          <h2>课程直播</h2>
          <p><button type="button" onClick={() => this.createGroup()}>创建群会话</button></p>
          <p><button type="button" onClick={() => this.createCourse()}>创建直播课程</button></p>
          <p><button type="button" onClick={() => this.getWatchData()}>查看直播数据</button></p>
        </div>
    );
  }
  createGroup(){
    let groupData = this.state.groupData;
    groupData.userId = this.state.userId;
    groupData.corpId = sessionStorage.getItem("corpId");
    axios.post(this.state.domain + "/live/createGroup", JSON.stringify(groupData),
        {headers:{"Content-Type":"application/json"}}
    ).then(res => {
      if (res && res.data.success) {
        if(res.data.data){
          this.setState({
            chatId:res.data.data.chatid,
            openConversationId:res.data.data.openConversationId
          })
          alert("创建群会话成功！")
        }else{
          alert("创建群会话失败！")
        }
      } else {
        alert("createGroup failed --->" + JSON.stringify(res));
      }
    }).catch(error => {
      alert("createGroup err, " + JSON.stringify(error))
    })
  }
  createCourse(){
    let courseData = this.state.courseData;
    courseData.userId = this.state.userId;
    courseData.corpId = sessionStorage.getItem("corpId");
    axios.post(this.state.domain + "/live/createCourse", JSON.stringify(courseData),
        {headers:{"Content-Type":"application/json"}}
    ).then(res => {
      if (res && res.data.success) {
        if(res.data.data){
          this.setState({
            courseId:res.data.data,
          })
          alert("创建直播课程成功！")
        }else{
          alert("创建直播课程失败！")
        }
      } else {
        alert("createCourse failed --->" + JSON.stringify(res));
      }
    }).catch(error => {
      alert("createCourse err, " + JSON.stringify(error))
    })
  }
  getWatchData(){
    const getWatchData = {
      userId: this.state.userId,
      corpId: sessionStorage.getItem("corpId"),
      chatId: this.state.chatId,
      feedId: this.state.courseId,
    };
    axios.post(this.state.domain + "/live/getWatchData", JSON.stringify(getWatchData),
        {headers:{"Content-Type":"application/json"}}
    ).then(res => {
      if (res && res.data.success) {
        if(res.data.data){
          this.setState({
            liveData:res.data.data,
          })
        }else{
          alert("获取直播数据失败！")
        }
      } else {
        alert("getWatchData failed --->" + JSON.stringify(res));
      }
    }).catch(error => {
      alert("getWatchData err, " + JSON.stringify(error))
    })
  }
  login(corpId) {
    let _this = this;
    dd.runtime.permission.requestAuthCode({
      corpId: corpId,//企业 corpId
      onSuccess : function(res) {
        // 调用成功时回调
        _this.state.authCode = res.code
        axios.get(_this.state.domain + "/login?authCode=" + _this.state.authCode + "&corpId=" + corpId
        ).then(res => {
          if (res && res.data.success) {
            let userId = res.data.data.userId;
            let userName = res.data.data.userName;
            alert('登录成功，你好' + userName);
            setTimeout(function () {
              _this.setState({
                userId:userId,
                userName:userName
              })
            }, 0)
          } else {
            alert("login failed --->" + JSON.stringify(res));
          }
        }).catch(error => {
          alert("httpRequest failed --->" + JSON.stringify(error))
        })
      },
      onFail : function(err) {
        // 调用失败时回调
        alert("requestAuthCode failed --->" + JSON.stringify(err))
      }
    });
  }

}

export default App;
