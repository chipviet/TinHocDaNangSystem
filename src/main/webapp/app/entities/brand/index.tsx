import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Brand from './brand';
import BrandDetail from './brand-detail';
import BrandUpdate from './brand-update';
import BrandDeleteDialog from './brand-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BrandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BrandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BrandDetail} />
      <ErrorBoundaryRoute path={match.url} component={Brand} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BrandDeleteDialog} />
  </>
);

export default Routes;
