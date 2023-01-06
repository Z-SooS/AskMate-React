import React from 'react';
import '../ComponentStyle/PostFooter.css';

function PostFooter({postDate,postScore,postTagArray, userObject}) {
    return (
        <footer className={"post-footer"}>
            <div className={"post-date"}>
                {postDate}
            </div>
            <div className={"post-score"}>
                {postScore}
            </div>
            {/*Todo add links to tags*/}
            <div className={"post-tags"}>
                {postTagArray.map(t => <a key={t.id}>{t.name}</a>)}
            </div>
            <div className={`post-user-name ${userObject.role === "ADMIN" ? "post-user-admin" : ""}`}>
                {userObject.username}
            </div>
            <div className={"post-user-reputation"}>
                {userObject.reputation}
            </div>
            <div className={"post-user-date"}>
                Here since: {userObject.dateCreated}
            </div>
        </footer>
    );
}

export default PostFooter;