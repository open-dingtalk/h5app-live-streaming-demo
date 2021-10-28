import react, { useEffect } from "react"
import { Table } from "antd"

const List = (props) => {
  const column = [
    {
      title: "观看回放时长 单位秒",
      dataIndex: "play_record_duration",
      key: "play_record_duration",
    },
    {
      title: "观看直播时长 单位秒",
      dataIndex: "play_live_duration",
      key: "play_live_duration",
    },
    {
      title: "用户名",
      dataIndex: "userid",
      key: "userid",
    },
  ]

  return (
    <div>
      <a onClick={props.onClick}>返回</a>
      <h4 className="title">直播数据</h4>
      <Table column={column} dataSource={props.viewer_watch_details}></Table>
    </div>
  )
}

export default List
