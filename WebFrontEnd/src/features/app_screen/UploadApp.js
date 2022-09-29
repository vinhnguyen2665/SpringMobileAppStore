import React from 'react';
import {Input, Upload, Button, Progress} from 'antd';
import axios from 'axios';
import {useUserLogin} from "./redux/userLoginActions";


export default function UploadApp() {
    const { TextArea } = Input;
    const {accessToken} = useUserLogin();
    const [file, setFile] = React.useState({});
    const [update_content, setUpdateContent] = React.useState({});
    const [uploadPercent, setUploadPercent] = React.useState(0);
    const [uploadStatus, setUploadStatus] = React.useState('active');
    const onRemoveFile = (e) => {
        console.log("onRemoveFile" + JSON.stringify(e))
    }
    const onChangeFile = (e) => {
        setFile(e.file.originFileObj);
        setUploadPercent(0);
        switch (e.status) {
            case 'uploading': {
                break;
            }
            case 'done': {
                console.log("File done" + JSON.stringify(e))
                break;
            }
        }


    }
    const uploadFile = (e) => {
        let formData = new FormData();
      //  let file = file;
       // let fileList = e.fileList;
        //let file = document.querySelector('#file_upload');
        //formData.append("file", file.files[0]);
        formData.append("file", file);
        if(update_content.target){
            formData.append("update_content", update_content.target.value);
        }
        const config = {
            onUploadProgress: function (progressEvent) {
                let percentCompleted = Math.round((progressEvent.loaded * 100) / progressEvent.total);
                setUploadPercent(percentCompleted);
                console.log('Percent: ' + percentCompleted + ' loaded: ' + progressEvent.loaded);
            },
            headers: {
                'Content-Type': 'multipart/form-data',
                'Authorization':  accessToken,
                'jwtToken': accessToken,

            }
        }

        axios.post('/api/file/upload-file', formData, config)
            .then(function (res) {
                setUploadStatus('');
                console.log(res);
            })
            .catch(function (event, xhr, options) {
                setUploadStatus('exception');
                console.log(event);
                console.log(xhr);
                console.log(options);
            });

    }

    return (
        <div>
            {/*         <Input id={"file_upload"} type={"file"} accept={".ipa,.apk"}
                   onChange={uploadFile} string={""}/>*/}
            <h1>Upload APP</h1>
            <Upload  accept={".ipa,.apk"} /*method={"POST"}
                     action={'/api/file/upload-file'}*/
                     onRemove={onRemoveFile}
                     onChange={onChangeFile}
                     withCredentials={true}>
                <Button>Select file</Button>
            </Upload>
            <Progress percent={uploadPercent} status={uploadStatus} />
            <TextArea id="txt_update_content" name={"update_content"} onChange={setUpdateContent} rows={4} />

            <Button onClick={uploadFile}>Submit</Button>

        </div>
    );
}
