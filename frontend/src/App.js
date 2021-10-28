import "./App.css"
import * as dd from "dingtalk-jsapi"
import axios from "axios"
import React from "react"
import { Button, message } from "antd"
import Course from "./components/Course"
import Group from "./components/Group"
import List from "./components/List"
import "./App.css"
import "antd/dist/antd.min.css"

class App extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      //内网穿透工具介绍:
      // https://developers.dingtalk.com/document/resourcedownload/http-intranet-penetration?pnamespace=app
      domain: "",
      corpId: "",
      authCode: "",
      userId: "",
      userName: "",
      chatId: "",
      courseId: "",
      openConversationId: "",
      groupData: {
        name: "直播群",
      },
      showType: 0,
      courseData: {
        coverUrl:
          "https://st-gdx.dancf.com/gaodingx/346/design/20190930-145735-2e58.png",
        anchorId: "",
        groupIds: "",
        appointBeginTime: 0,
        title: "测试课程",
        feedType: 0,
        introduction: "简介",
      },
      viewer_watch_details: [],
    }
  }
  render() {
    const corpId = window.location.href.split("=")[1]
    if (this.state.corpId === "") {
      sessionStorage.setItem("corpId", corpId)
    }
    if (this.state.userId === "") {
      this.login(corpId)
    }
    return (
      <div className="App">
        {this.state.showType === 2 && <h2>课程直播</h2>}
        {this.state.showType === 1 && (
          <Group onCreateGroup={(e) => this.createGroup(e)} />
        )}
        {this.state.showType === 2 && (
          <Course
            onClick={(e) => this.createCourse(e)}
            onCreateGroup={(e) => this.createGroup(e)}
          />
        )}
        {this.state.showType === 3 && (
          <List
            onClick={() => this.setState({ showType: 0 })}
            viewer_watch_details={this.state.viewer_watch_details}
          />
        )}
        {!this.state.showType && (
          <p>
            <Button
              type="primary"
              onClick={() => this.setState({ showType: 1 })}
            >
              创建场景群
            </Button>
          </p>
        )}
        {!this.state.showType && (
          <p>
            <Button
              type="primary"
              onClick={() => this.setState({ showType: 2 })}
            >
              创建直播课程
            </Button>
          </p>
        )}
        {/*<p><button type="button" onClick={() => this.liveRun()}>开启直播</button></p>*/}
        {!this.state.showType && (
          <p>
            <Button type="primary" onClick={() => this.getWatchData()}>
              查看直播数据
            </Button>
          </p>
        )}
        <a
          className="AppLink"
          onClick={() => {
            if (this.state.courseId) {
              window.location.href = `dingtalk://dingtalkclient/action/start_uniform_live?liveUuid=${this.state.courseId}`
            } else {
              message.error("请先创建直播课程")
            }
          }}
        >
          查看直播课程
        </a>
      </div>
    )
  }
  liveRun() {}
  createGroup(groupDatas) {
    groupDatas.userId = this.state.userId
    groupDatas.corpId = sessionStorage.getItem("corpId")
    axios
      .post(
        this.state.domain + "/live/createGroup",
        JSON.stringify(groupDatas),
        { headers: { "Content-Type": "application/json" } }
      )
      .then((res) => {
        if (res && res.data.success) {
          if (res.data.data) {
            this.setState({
              openConversationId: res.data.data,
              showType: 0,
            })
            message.success("创建场景群成功！")
          } else {
            message.error("创建场景群失败！")
          }
        } else {
          alert("createGroup failed --->" + JSON.stringify(res))
        }
      })
      .catch((error) => {
        alert("createGroup err, " + JSON.stringify(error))
      })
  }
  createCourse(courseData) {
    const { appointBeginTime } = courseData
    courseData.anchorId = this.state.userId
    courseData.groupIds = this.state.openConversationId
    courseData.corpId = sessionStorage.getItem("corpId")
    courseData.appointBeginTime = new Date(appointBeginTime).getTime()
    axios
      .post(
        this.state.domain + "/live/createCourse",
        JSON.stringify(courseData),
        { headers: { "Content-Type": "application/json" } }
      )
      .then((res) => {
        if (res && res.data.success) {
          if (res.data.data) {
            this.setState({
              courseId: res.data.data,
              showType: 0,
            })
            message.success("创建直播课程成功！")
          } else {
            message.error("创建直播课程失败！")
          }
        } else {
          alert("createCourse failed --->" + JSON.stringify(res))
        }
      })
      .catch((error) => {
        alert("createCourse err, " + JSON.stringify(error))
      })
  }
  getWatchData() {
    const getWatchData = {
      userId: this.state.userId,
      corpId: sessionStorage.getItem("corpId"),
      chatId: this.state.chatId,
      feedId: this.state.courseId,
    }
    axios
      .post(
        this.state.domain + "/live/getWatchData",
        JSON.stringify(getWatchData),
        { headers: { "Content-Type": "application/json" } }
      )
      .then((res) => {
        if (res && res.data.success) {
          if (res.data.data) {
            this.setState({
              viewer_watch_details: res.data.data,
              showType: 3,
            })
          } else {
            message.error("获取直播数据失败！")
          }
        } else {
          message.error("暂无数据")
        }
      })
      .catch((error) => {})
  }
  login(corpId) {
    let _this = this
    dd.runtime.permission.requestAuthCode({
      corpId: corpId, //企业 corpId
      onSuccess: function (res) {
        // 调用成功时回调
        _this.state.authCode = res.code
        axios
          .get(
            _this.state.domain +
              "/login?authCode=" +
              _this.state.authCode +
              "&corpId=" +
              corpId
          )
          .then((res) => {
            if (res && res.data.success) {
              let userId = res.data.data.userId
              let userName = res.data.data.userName
              message.success("登录成功，你好" + userName)
              setTimeout(function () {
                _this.setState({
                  userId: userId,
                  userName: userName,
                })
              }, 0)
            } else {
              alert("login failed --->" + JSON.stringify(res))
            }
          })
          .catch((error) => {
            alert("httpRequest failed --->" + JSON.stringify(error))
          })
      },
      onFail: function (err) {
        // 调用失败时回调
        alert("requestAuthCode failed --->" + JSON.stringify(err))
      },
    })
  }
}

export default App
