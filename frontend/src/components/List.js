import react, { useEffect } from "react"
import { Table } from "antd"

const List = (props) => {
  const column = [
    {
      title: "观看回放时长（单位:秒）",
      dataIndex: "playRecordDuration",
      key: "playRecordDuration",
    },
    {
      title: "观看直播时长（单位:秒）",
      dataIndex: "playLiveDuration",
      key: "playLiveDuration",
    },
    {
      title: "用户名",
      dataIndex: "userName",
      key: "userName",
    },
  ]

  return (
    <div className="table">
      {console.log(props.viewer_watch_details)}
      <a onClick={props.onClick}>←返回</a>
      <h4 className="title">直播数据</h4>
      <Table
        columns={column}
        dataSource={props.viewer_watch_details}
        pagination={false}
      />
    </div>
  )
}

export default List
