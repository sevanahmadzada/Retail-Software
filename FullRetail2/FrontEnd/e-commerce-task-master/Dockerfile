



#------------------------------------------

#FROM node:17-alpine as build
#
#WORKDIR /app
#
#COPY package.json package-lock.json ./
#
#RUN npm install
#
#COPY . ./
##
##ARG REACT_APP_API_BASE_URL
##ENV REACT_APP_API_BASE_URL=${REACT_APP_API_BASE_URL}
#
#RUN npm run build
#
#FROM nginx
#
#COPY --from=build /app/build /var/www
#
#COPY nginx.conf /etc/nginx/nginx.conf
#
#EXPOSE 3000
#
#ENTRYPOINT ["nginx","-g","daemon off;"]

#-------------------------------------------

FROM node:17-alpine

WORKDIR /app

COPY package.json package.json

RUN npm install

COPY . .

EXPOSE 3000:3000

CMD ["npm","start"]