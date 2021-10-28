import react, { useEffect } from "react"
import { Form, Input, Button } from "antd"

const Course = (props) => {
  const [form] = Form.useForm()
  useEffect(() => {
    form.setFieldsValue({
      name: "直播群",
    })
  }, [])

  const onSubmit = (data) => {
    props.onCreateGroup(data)
  }

  return (
    <div>
      <h4 className="title">创建直播群</h4>
      <Form form={form} onFinish={onSubmit}>
        <Form.Item label="直播群群名" name="name">
          <Input placeholder="请输入直播群群名" />
        </Form.Item>
        <Button htmlType="submit" type="primary">
          创建
        </Button>
      </Form>
    </div>
  )
}

export default Course
