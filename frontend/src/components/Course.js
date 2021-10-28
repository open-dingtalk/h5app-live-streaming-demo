import react, { useEffect } from "react"
import { Form, Input, Button, DatePicker } from "antd"
import moment from "moment"

const Course = (props) => {
  const [form] = Form.useForm()
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

  const disabledDate = (current) => {
    return current && current < moment().endOf("day")
  }
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
          rules={[{ required: true, message: "直播时间必填" }]}
        >
          <DatePicker
            showTime
            placeholder="请选择时间"
            disabledDate={disabledDate}
          />
        </Form.Item>
        <Form.Item label="简介" name="introduction">
          <Input placeholder="请输入简介" />
        </Form.Item>
        <Button htmlType="submit" type="primary">
          创建
        </Button>
      </Form>
    </div>
  )
}

export default Course
