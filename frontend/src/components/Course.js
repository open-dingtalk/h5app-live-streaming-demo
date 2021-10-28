import react, { useEffect, useState } from "react"
import { Form, Input, Button } from "antd"
import { DatePicker } from "antd-mobile"
import moment from "moment"

const Course = (props) => {
  const [form] = Form.useForm()
  const [pickerV, setPickerV] = useState(false)
  const [time, settime] = useState(null)
  useEffect(() => {
    form.setFieldsValue({
      coverUrl:
        "https://st-gdx.dancf.com/gaodingx/346/design/20190930-145735-2e58.png",
      anchorId: "",
      groupIds: "",
      appointBeginTime: null,
      title: "测试课程",
      feedType: 0,
      introduction: "简介",
    })
  }, [])

  const onSubmit = (data) => {
    props.onClick(data)
  }

  const now = new Date()
  return (
    <div>
      <h4 className="title">创建直播课程</h4>
      <Form form={form} onFinish={onSubmit}>
        <Form.Item label="直播标题" name="title">
          <Input placeholder="请输入直播标题" />
        </Form.Item>
        <Form.Item label="直播图片链接" name="coverUrl">
          <Input placeholder="请输入直播图片链接" />
        </Form.Item>
        <Form.Item
          label="直播开始时间"
          name="appointBeginTime"
          rules={[{ required: true, message: "直播时间必选，提前两小时" }]}
        >
          <div className="table">
            <Button type="primary" onClick={() => setPickerV(true)}>
              {time ? "已选择开播时间" : "选择开播时间"}
            </Button>
          </div>

          <DatePicker
            visible={pickerV}
            onClose={() => {
              setPickerV(false)
            }}
            min={new Date(now.setHours(now.getHours() + 2))}
            precision="minute"
            onConfirm={(val, s) => {
              settime(val)
              form.setFieldsValue({
                appointBeginTime: val,
              })
            }}
          >
            {(value) => {
              return value
                ? moment(value).format("YYYY-MM-DD HH:mm")
                : moment(now).format("YYYY-MM-DD HH:mm:ss")
            }}
          </DatePicker>
        </Form.Item>
        <Form.Item label="简介" name="introduction">
          <Input placeholder="请输入简介" />
        </Form.Item>
        <Button htmlType="submit" type="primary" className="table">
          创建
        </Button>
      </Form>
    </div>
  )
}

export default Course
