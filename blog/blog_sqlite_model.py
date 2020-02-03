import pickle


def save_blog_list(blog_list):
    ''' list 자료형의 블로그 목록을 저장한다 '''
    with open('blog.pickle', 'wb') as f:
        pickle.dump(blog_list, f)


def get_blog_list():
    ''' pickle로 저장된 list 자료형의 블로그 목록을 리턴한다 '''
    import os
    if os.path.exists('blog.pickle'):
        with open('blog.pickle', 'rb') as f:
            blog_list = pickle.load(f)
    else:
        blog_list = []
    return blog_list


def add_blog(subject, content):
    ''' 블로그를 추가한다 '''
    import time
    today = time.strftime('%Y%m%d')
    blog_list = get_blog_list()

    max_id = 0
    for blog in blog_list:
        if blog['id'] > max_id:
            max_id = blog['id']
    next_id = max_id + 1

    blog = {
      "id":next_id,
      "subject":subject,
      "content":content,
      "date":today
    }
    blog_list.append(blog)
    save_blog_list(blog_list)


def find_blog(blog_list, _id):
    ''' 블로그 목록에서 고유번호로 블로그를 찾아서 리턴한다 '''
    for blog in blog_list:
        if blog['id'] == _id:
            return blog


def read_blog(_id):
    ''' 고유번호에 해당되는 블로그를 리턴한다 '''
    blog_list = get_blog_list()
    blog = find_blog(blog_list, _id)
    return blog


def modify_blog(_id, subject, content):
    ''' 고유번호에 해당되는 블로그를 수정한다 '''
    blog_list = get_blog_list()
    blog = find_blog(blog_list, _id)
    blog["subject"] = subject
    blog["content"] = content
    save_blog_list(blog_list)


def remove_blog(_id):
    ''' 고유번호에 해당되는 블로그를 삭제한다 '''
    blog_list = get_blog_list()
    blog = find_blog(blog_list, _id)
    blog_list.remove(blog)
    save_blog_list(blog_list)
