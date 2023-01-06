import React from 'react';
import '../ComponentStyle/PostContainer.css';
import PostFooter from "./PostFooter";
import PostAnswerContainer from "./PostAnswerContainer";


function PostContainer({postObject}) {
    return (
        <article className={"post-container"} data-id={postObject.post.id}>
            <header className={"post-title"}>{postObject.post.title}</header>
            <section className={"post-message"}>
                {postObject.post.message}
            </section>
            <PostFooter postDate={postObject.post.dateCreated} postScore={postObject.post.score} postTagArray={postObject.post.tags} userObject={postObject.post.user}/>
            <section className={"answer-display"}>
                {postObject.answers.map(answer => (<PostAnswerContainer answerObject={answer} key={answer.id}/>))}
            </section>
        </article>
    );
}

export default PostContainer;