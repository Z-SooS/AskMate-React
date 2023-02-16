import React from 'react';
import {Link} from "react-router-dom";
import Tagbox from "./Tagbox";

function PostsPageItem({postObject}) {
    const postDate = new Date(postObject.dateCreated);
    const userDate = new Date(postObject.user.dateCreated);
    return (
        <article className={'post-item'}>
            <header>
                <Link to={`/post/${postObject.id}`} className={'post-item-title'}>{postObject.title}</Link>
                <Tagbox tagObjects={postObject.tags}/>
            </header>
            <footer className={'post-info'}>
                <div className={'answer-counter'}>Answers: {postObject.numberOfAnswers}</div>
                <div className={'score'}>Score: {postObject.score}</div>
                <div className={'post-date'}>Created: {postDate.toLocaleString()}</div>
            </footer>
            <aside className={'post-userinfo'}>
                <Link to={`/user/${postObject.user.username}`}>{postObject.user.username}</Link>
                <span className={'join-date'}>User since: {userDate.toLocaleString()}</span>
            </aside>
        </article>
    );
}

export default PostsPageItem;