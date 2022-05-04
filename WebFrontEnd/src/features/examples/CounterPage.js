import React from 'react';
import {Input, Upload, Button} from 'antd';
import {useCounterPlusOne, useCounterMinusOne, useCounterReset} from './redux/hooks';
import axios from 'axios';
import {useUserLogin} from "./redux/userLoginActions";


export default function CounterPage() {
    const {count, counterPlusOne} = useCounterPlusOne();
    const {counterMinusOne} = useCounterMinusOne();
    const {counterReset} = useCounterReset();
    const {accessToken} = useUserLogin();
    const onRemoveFile = (e) => {
        console.log("onRemoveFile" + JSON.stringify(e))
    }
    const onChangeFile = (e) => {
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
        let file = e.file;
        let fileList = e.fileList;
        //let file = document.querySelector('#file_upload');
        //formData.append("file", file.files[0]);
        formData.append("file", file);

        const config = {
            onUploadProgress: function (progressEvent) {
                let percentCompleted = Math.round((progressEvent.loaded * 100) / progressEvent.total);
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
                console.log(res);
            })
            .catch(function (event, xhr, options) {
                console.log(event);
                console.log(xhr);
                console.log(options);
            });

    }

    return (
        <div className="examples-counter-page">
            {/*         <Input id={"file_upload"} type={"file"} accept={".ipa,.apk"}
                   onChange={uploadFile} string={""}/>*/}

            <Upload  accept={".ipa,.apk"} method={"POST"}
                     action={'/api/file/upload-file'}
                     onRemove={onRemoveFile}
                     onChange={onChangeFile}
                     withCredentials={true}>
                <Button>Select file</Button>
            </Upload>
            <h1>Counter</h1>
            <p>This is simple counter demo to show how Redux sync actions work.</p>
            <button className="btn-minus-one" onClick={counterMinusOne} disabled={count === 0}>
                -
            </button>
            <span>{count}</span>
            <button className="btn-plus-one" onClick={counterPlusOne}>
                +
            </button>
            <button className="btn-reset" onClick={counterReset}>
                Reset
            </button>
        </div>
    );
}
